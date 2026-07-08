package com.jiradar.jiradarback.model.issuetracker;

import com.jiradar.jiradarback.model.datetime.DateRange;
import com.jiradar.jiradarback.service.UserMetricCalculationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMetrics {

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
	private final Map<String, Double> jiraRateByType;

	public static UserMetrics generate(User user, List<Issue> projectIssues, DateRange range) {

		UserMetricCalculationService metricsService = new UserMetricCalculationService(user, projectIssues, range);

		return UserMetrics.builder()
				.from(range.from())
				.to(range.to())
				.numberOfIssueStarted(metricsService.getNumberOfIssueStarted())
				.numberOfIssueDone(metricsService.getNumberOfIssueDone())
				.numberOfReviewDone(metricsService.getNumberOfReviewDone())
				.numberOfReviewReopened(metricsService.getNumberOfReviewReopened())
				.averageCycleTime(metricsService.calculateAverageCycleTime())
				.averageReviewTime(metricsService.calculateAverageReviewTime())
				.teamReviewParticipationRate(metricsService.calculateTeamReviewParticipationRate())
				.deliverySuccessRate(metricsService.calculateDeliverySuccessRate())
				.pingPongReviewRate(metricsService.calculatePingPongReviewRate())
				.parallelJiraInProgressRate(metricsService.calculateParallelJiraInProgressRate())
				.jiraRateByType(metricsService.getDoneIssuesTypeDistribution())
				.build();
	}
}