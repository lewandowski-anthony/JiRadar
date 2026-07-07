package com.jiradar.jiradarback.model.jira;

import com.jiradar.jiradarback.model.datetime.DateRange;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
	private final double parallelJiraInProgressRate;
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

		List<JiraIssue> issuesDoneInPeriod = userIssues.stream().filter(issue -> issue.isDoneIn(range)).toList();

		long numberOfJiraStartedDuringPeriod = userIssues.stream().filter(issue -> issue.isStartedIn(range)).count();
		long numberOfJiraDoneDuringPeriod = issuesDoneInPeriod.size();
		long numberOfReviewReopenedDuringPeriod = userIssues.stream().mapToLong(issue -> issue.getReviewReopenedCount(range)).sum();

		Duration avgCycle = calculateAverage(issuesDoneInPeriod.stream()
				.map(JiraIssue::calculateCycleTimeForIssue)
				.filter(Objects::nonNull)
				.toList());

		Duration avgReview = calculateAverage(devReviewDurations);
		double deliverySuccessRate = calculateActionPercentage(numberOfJiraDoneDuringPeriod, numberOfJiraStartedDuringPeriod);
		double altruism = calculateActionPercentage(devReviewDurations.size(), teamReviewDurations.size());
		double pingPongReviewRate = numberOfJiraDoneDuringPeriod == 0 ? 0.0
									: Math.round(((double) numberOfReviewReopenedDuringPeriod / numberOfJiraDoneDuringPeriod) * 100.0) / 100.0;

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
				.parallelJiraInProgressRate(calculateParallelJiraInProgressRate(userIssues, range))
				.build();
	}

	private static double calculateParallelJiraInProgressRate(List<JiraIssue> userIssues, DateRange range) {
		ZonedDateTime startDay = range.from().truncatedTo(ChronoUnit.DAYS);
		ZonedDateTime endDay = range.to().truncatedTo(ChronoUnit.DAYS);

		long totalDays = ChronoUnit.DAYS.between(startDay, endDay) + 1;
		if (totalDays <= 0) return 0.0;

		long totalActiveTicketsCombined = Stream.iterate(startDay, day -> day.plusDays(1))
				.limit(totalDays)
				.mapToLong(day -> userIssues.stream()
						.filter(issue -> issue.isActiveOn(day))
						.count())
				.sum();

		double rawWip = (double) totalActiveTicketsCombined / totalDays;
		return Math.round(rawWip * 100.0) / 100.0;
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