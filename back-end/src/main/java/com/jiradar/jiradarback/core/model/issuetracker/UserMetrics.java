package com.jiradar.jiradarback.core.model.issuetracker;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.service.UserMetricCalculationService;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.service.CustomMetricEngine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UserMetrics {

	private final ZonedDateTime from;
	private final ZonedDateTime to;
	private final Metric metric;
	private final List<PeriodicUserMetrics> userMetricsByGranularity;

	public static UserMetrics generate(MetricGenerationQuery query) {

		CustomMetricEngine customMetricEngine = new CustomMetricEngine(query.customMetricsDefinition());

		UserMetricCalculationService globalService = new UserMetricCalculationService(
				query.user(),
				query.projectIssues(),
				query.range()
		);

		Metric globalMetric = new Metric(globalService, customMetricEngine);

		List<PeriodicUserMetrics> periodicHistory = (query.granularity() != null)
													? generateHistory(query, customMetricEngine)
													: null;

		return UserMetrics.builder()
				.from(query.range().from())
				.to(query.range().to())
				.metric(globalMetric)
				.userMetricsByGranularity(periodicHistory)
				.build();
	}

	private static List<PeriodicUserMetrics> generateHistory(MetricGenerationQuery query, CustomMetricEngine engine) {
		return query.range().splitBy(query.granularity()).stream()
				.map(subRange -> {
					List<Issue> subIssues = query.projectIssues().stream()
							.filter(issue -> issue.isStartedIn(subRange)
									|| issue.isDoneIn(subRange)
									|| issue.isActiveOn(subRange.from()))
							.toList();

					return new PeriodicUserMetrics(
							subRange.from(),
							subRange.to(),
							query.granularity().toLabel(subRange.from()),
							new Metric(new UserMetricCalculationService(query.user(), subIssues, subRange), engine)
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

		private Metric(UserMetricCalculationService service, CustomMetricEngine engine) {
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
					engine.evaluateCustomMetrics(service)
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
			List<CustomMetricDefinition> customMetricsDefinition
	) {}
}