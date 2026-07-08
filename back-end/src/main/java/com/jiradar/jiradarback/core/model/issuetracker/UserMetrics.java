package com.jiradar.jiradarback.core.model.issuetracker;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.UserMetricCalculationService;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMetrics {

	private final ZonedDateTime from;
	private final ZonedDateTime to;
	private final Metric metric;
	private final List<PeriodicUserMetrics> history;

	public static UserMetrics generate(User user, List<Issue> projectIssues, DateRange range) {
		return UserMetrics.generate(user, projectIssues, range, null);
	}

	public static UserMetrics generate(User user, List<Issue> projectIssues, DateRange range, TimeGranularity granularity) {

		Metric globalMetric = new Metric(user, projectIssues, range);

		List<PeriodicUserMetrics> periodicHistory = (granularity != null)
													? generateHistory(user, projectIssues, range, granularity)
													: null;

		return UserMetrics.builder()
				.from(range.from())
				.to(range.to())
				.metric(globalMetric)
				.history(periodicHistory)
				.build();
	}

	private static List<PeriodicUserMetrics> generateHistory(User user, List<Issue> projectIssues, DateRange range, TimeGranularity granularity) {
		return range.splitBy(granularity).stream()
				.map(subRange -> {
					List<Issue> subIssues = projectIssues.stream()
							.filter(issue -> issue.isStartedIn(subRange)
									|| issue.isDoneIn(subRange)
									|| issue.isActiveOn(subRange.from()))
							.toList();

					return new PeriodicUserMetrics(
							subRange.from(),
							subRange.to(),
							granularity.toLabel(subRange.from()),
							new Metric(user, subIssues, subRange)
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
			Map<String, Double> issueRateByType
	) {

		public Metric(User user, List<Issue> projectIssues, DateRange range) {
			this(new UserMetricCalculationService(user, projectIssues, range));
		}

		private Metric(UserMetricCalculationService service) {
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
					service.getDoneIssuesTypeDistribution()
			);
		}
	}

	public record PeriodicUserMetrics(
			ZonedDateTime from,
			ZonedDateTime to,
			String label,
			Metric metrics
	) {}
}