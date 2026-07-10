package com.jiradar.jiradarback.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Schema(description = "${openapi.dto.metrics.description}")
public record UserMetricsDto(
		@Schema(description = "${openapi.dto.metrics.from}")
		ZonedDateTime from,

		@Schema(description = "${openapi.dto.metrics.to}")
		ZonedDateTime to,

		@JsonUnwrapped
		@Schema(description = "${openapi.dto.metrics.metric}")
		MetricDto metric,

		@JsonInclude(NON_NULL)
		@Schema(description = "${openapi.dto.metrics.userMetricsByGranularity}")
		List<PeriodicUserMetricsDto> userMetricsByGranularity
) {

	@Schema(description = "${openapi.dto.metric.description}")
	public record MetricDto(
			@Schema(description = "${openapi.dto.metric.numberOfIssueStarted}", example = "14")
			long numberOfIssueStarted,

			@Schema(description = "${openapi.dto.metric.numberOfIssueDone}", example = "11")
			long numberOfIssueDone,

			@Schema(description = "${openapi.dto.metric.averageCycleTime}", example = "PT48H30M")
			String averageCycleTime,

			@Schema(description = "${openapi.dto.metric.averageReviewTime}", example = "PT4H15M")
			String averageReviewTime,

			@Schema(description = "${openapi.dto.metric.numberOfReviewDone}", example = "32")
			long numberOfReviewDone,

			@Schema(description = "${openapi.dto.metric.numberOfReviewReopened}", example = "3")
			long numberOfReviewReopened,

			@Schema(description = "${openapi.dto.metric.teamReviewParticipationRate}", example = "72.5")
			double teamReviewParticipationRate,

			@Schema(description = "${openapi.dto.metric.deliverySuccessRate}", example = "91.0")
			double deliverySuccessRate,

			@Schema(description = "${openapi.dto.metric.pingPongReviewRate}", example = "1.1")
			double pingPongReviewRate,

			@Schema(description = "${openapi.dto.metric.parallelIssuesInProgressRate}", example = "18.4")
			double parallelIssuesInProgressRate,

			@Schema(description = "${openapi.dto.metric.issueRateByType}")
			List<IssueRateByTypeDto> issueRateByType
	) {}

	public record IssueRateByTypeDto (
			String type,
			Double rate
	) {}

	@Schema(description = "${openapi.dto.periodic.description}")
	public record PeriodicUserMetricsDto(
			@Schema(description = "${openapi.dto.periodic.from}")
			ZonedDateTime from,

			@Schema(description = "${openapi.dto.periodic.to}")
			ZonedDateTime to,

			@Schema(description = "${openapi.dto.periodic.label}", example = "Sem. 28 - 2026")
			String label,

			@JsonUnwrapped
			@Schema(description = "${openapi.dto.periodic.metrics}")
			MetricDto metrics
	) {}
}