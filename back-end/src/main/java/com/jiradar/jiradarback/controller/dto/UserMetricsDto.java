package com.jiradar.jiradarback.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record UserMetricsDto(
		ZonedDateTime from,
		ZonedDateTime to,
		@JsonUnwrapped
		MetricDto metric,
		@JsonInclude(NON_NULL)
		List<PeriodicUserMetricsDto> userMetricsByGranularity
) {

	public record MetricDto(
			long numberOfIssueStarted,
			long numberOfIssueDone,
			String averageCycleTime,
			String averageReviewTime,
			long numberOfReviewDone,
			long numberOfReviewReopened,
			double teamReviewParticipationRate,
			double deliverySuccessRate,
			double pingPongReviewRate,
			double parallelIssuesInProgressRate,
			Map<String, Double> issueRateByType
	) {}

	public record PeriodicUserMetricsDto(
			ZonedDateTime from,
			ZonedDateTime to,
			String label,
			@JsonUnwrapped
			MetricDto metrics
	) {}
}