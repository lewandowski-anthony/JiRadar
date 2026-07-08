package com.jiradar.jiradarback.controller.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public record UserMetricsDto(
		ZonedDateTime from,
		ZonedDateTime to,
		@JsonUnwrapped
		MetricDto metric,
		List<PeriodicUserMetricsDto> history
) {

	public record MetricDto(
			long numberOfIssueStarted,
			long numberOfIssueDone,
			Duration averageCycleTime,
			Duration averageReviewTime,
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