package com.jiradar.jiradarback.model.jira;

import com.jiradar.jiradarback.model.datetime.DateRange;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JiraUserMetrics {

	private final ZonedDateTime from;
	private final ZonedDateTime to;
	private final long numberOfIssueStarted;
	private final long numberOfIssueDone;
	private final Duration averageCycleTime;
	private final Duration averageReviewTime;
	private final long numberOfReviewDone;
	private final long numberOfReviewReopened;
	private final double teamReviewParticipationRate;
	private final double deliverySuccessRate;
	private final double pingPongReviewRate;
	private final JiraUser user;

	public static JiraUserMetrics generate(JiraUser user, List<JiraIssue> projectIssues, DateRange range) {

		List<JiraIssue> userIssues = projectIssues.stream()
				.filter(issue -> issue.isAssignedTo(user.getEmail()))
				.toList();

		List<Duration> devReviewDurations = projectIssues.stream()
				.filter(issue -> !issue.isAssignedTo(user.getEmail()))
				.flatMap(issue -> issue.getReviewDurations(range, change -> change.isReviewActionBy(user.getEmail())).stream())
				.toList();

		List<Duration> teamReviewDurations = projectIssues.stream()
				.filter(issue -> !issue.isAssignedTo(user.getEmail()))
				.flatMap(issue -> issue.getReviewDurations(range, change -> change.getAuthor() != null
						&& StringUtils.isNotBlank(change.getAuthor().getEmail())
						&& change.isReviewActionBy(change.getAuthor().getEmail())).stream()
				)
				.toList();

		long numberOfJiraStartedDuringPeriod = userIssues.stream().filter(issue -> issue.isStartedIn(range)).count();
		long numberOfJiraDoneDuringPeriod = userIssues.stream().filter(issue -> issue.isStartedIn(range) && issue.isDoneIn(range)).count();
		long numberOfReviewReopenedDuringPeriod = userIssues.stream().mapToLong(issue -> issue.getReviewReopenedCount(range)).sum();

		Duration avgCycle = calculateAverage(userIssues.stream().map(JiraIssue::calculateCycleTimeForIssue).filter(Objects::nonNull).toList());
		Duration avgReview = calculateAverage(devReviewDurations);
		double deliverySuccessRate = calculateActionPercentage(numberOfJiraDoneDuringPeriod, numberOfJiraStartedDuringPeriod);
		double altruism = calculateActionPercentage(devReviewDurations.size(), teamReviewDurations.size());
		double pingPongReviewRate = calculateActionPercentage(numberOfReviewReopenedDuringPeriod, numberOfJiraDoneDuringPeriod);

		return JiraUserMetrics.builder()
				.from(range.from())
				.to(range.to())
				.numberOfIssueStarted(numberOfJiraStartedDuringPeriod)
				.numberOfIssueDone(numberOfJiraDoneDuringPeriod)
				.averageCycleTime(avgCycle)
				.averageReviewTime(avgReview)
				.numberOfReviewDone(devReviewDurations.size())
				.numberOfReviewReopened(numberOfReviewReopenedDuringPeriod)
				.teamReviewParticipationRate(altruism)
				.pingPongReviewRate(pingPongReviewRate)
				.user(user)
				.deliverySuccessRate(deliverySuccessRate)
				.build();
	}

	private static Duration calculateAverage(List<Duration> durations) {
		if (durations.isEmpty()) return Duration.ZERO;
		return durations.stream().reduce(Duration.ZERO, Duration::plus).dividedBy(durations.size());
	}

	private static double calculateActionPercentage(long devActions, long teamActions) {
		if (teamActions == 0) return 100.0;
		double rate = ((double) devActions / teamActions) * 100.0;
		return Math.round(rate * 100.0) / 100.0;
	}
}