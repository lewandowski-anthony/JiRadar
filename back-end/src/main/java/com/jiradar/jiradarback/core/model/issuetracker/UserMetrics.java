package com.jiradar.jiradarback.core.model.issuetracker;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.UserMetricCalculationService;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.service.CustomMetricEngine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMetrics {

	private final ZonedDateTime from;
	private final ZonedDateTime to;
	private final Metric metric;
	private final List<PeriodicUserMetrics> userMetricsByGranularity;

	public static UserMetrics generate(MetricGenerationQuery metricGenerationQuery) {

		UserMetricCalculationService userMetricCalculationService = new UserMetricCalculationService(metricGenerationQuery.user(), metricGenerationQuery.projectIssues(), metricGenerationQuery.range());

		Metric globalMetric = new Metric(userMetricCalculationService, metricGenerationQuery.customFormulas());

		List<PeriodicUserMetrics> periodicHistory = (metricGenerationQuery.granularity() != null)
													? generateHistory(metricGenerationQuery)
													: null;

		return UserMetrics.builder()
				.from(metricGenerationQuery.range().from())
				.to(metricGenerationQuery.range().to())
				.metric(globalMetric)
				.userMetricsByGranularity(periodicHistory)
				.build();
	}

	private static List<PeriodicUserMetrics> generateHistory(MetricGenerationQuery metricGenerationQuery) {
		return metricGenerationQuery.range().splitBy(metricGenerationQuery.granularity()).stream()
				.map(subRange -> {
					List<Issue> subIssues = metricGenerationQuery.projectIssues().stream()
							.filter(issue -> issue.isStartedIn(subRange)
									|| issue.isDoneIn(subRange)
									|| issue.isActiveOn(subRange.from()))
							.toList();

					return new PeriodicUserMetrics(
							subRange.from(),
							subRange.to(),
							metricGenerationQuery.granularity().toLabel(subRange.from()),
							new Metric(new UserMetricCalculationService(metricGenerationQuery.user(), subIssues, subRange), metricGenerationQuery.customFormulas())
					);
				})
				.toList();
	}

	public record Metric(
			long numberOfIssueStarted,
			long numberOfIssueDone,
			java.time.Duration averageCycleTime,
			java.time.Duration averageReviewTime,
			long numberOfReviewDone,
			long numberOfReviewReopened,
			double teamReviewParticipationRate,
			double deliverySuccessRate,
			double pingPongReviewRate,
			double parallelIssuesInProgressRate,
			Map<String, Double> issueRateByType,
			Map<String, Object> customMetrics
	) {

		private Metric(UserMetricCalculationService service, Map<String, String> customMetrics) {
			this(
					service.getNumberOfIssueStarted(),
					service.getNumberOfIssueDone(),
					service.calculateAverageCycleTime(),
					service.calculateAverageReviewTime(),
					service.getNumberOfReviewDone(),
					service.getNumberOfReviewReopened(),
					service.calculateTeamReviewParticipationRate(),
					service.calculateDeliverySuccessRate(),
					service.calculatePingPongReviewRate(),
					service.calculateParallelJiraInProgressRate(),
					service.getDoneIssuesTypeDistribution(),
					new CustomMetricEngine(customMetrics).evaluateCustomMetrics(service)
			);
		}
	}

	public record PeriodicUserMetrics(
			ZonedDateTime from,
			ZonedDateTime to,
			String label,
			Metric metrics
	) {}

	@Builder
	public record MetricGenerationQuery(
			User user,
			List<Issue> projectIssues,
			DateRange range,
			TimeGranularity granularity,
			Map<String, String> customFormulas
	) {}
}