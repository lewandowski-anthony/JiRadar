package com.jiradar.jiradarback.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "${openapi.dto.description}")
public record UserMetricsDto(
		@Schema(description = "${openapi.dto.from}")
		ZonedDateTime from,

		@Schema(description = "${openapi.dto.to}")
		ZonedDateTime to,

		@JsonUnwrapped
		MetricDto metric,

		@Schema(description = "${openapi.dto.history}")
		List<PeriodicUserMetricsDto> userMetricsByGranularity
) {
	@Builder
	@Schema(description = "${openapi.dto.metric.description}")
	public record MetricDto(
			@Schema(description = "${openapi.dto.metric.numberOfIssueStarted}")
			long numberOfIssueStarted,

			@Schema(description = "${openapi.dto.metric.numberOfIssueDone}")
			long numberOfIssueDone,

			@Schema(description = "${openapi.dto.metric.averageCycleTime}")
			String averageCycleTime,

			@Schema(description = "${openapi.dto.metric.averageReviewTime}")
			String averageReviewTime,

			@Schema(description = "${openapi.dto.metric.numberOfReviewDone}")
			long numberOfReviewDone,

			@Schema(description = "${openapi.dto.metric.numberOfReviewReopened}")
			long numberOfReviewReopened,

			@Schema(description = "${openapi.dto.metric.teamReviewParticipationRate}")
			double teamReviewParticipationRate,

			@Schema(description = "${openapi.dto.metric.deliverySuccessRate}")
			double deliverySuccessRate,

			@Schema(description = "${openapi.dto.metric.pingPongReviewRate}")
			double pingPongReviewRate,

			@Schema(description = "${openapi.dto.metric.parallelIssuesInProgressRate}")
			double parallelIssuesInProgressRate,

			@Schema(description = "${openapi.dto.metric.issueRateByType}")
			List<IssueRateByTypeDto> issueRateByType,

			List<CustomMetricElementDto> customMetrics
	) {}

	public record IssueRateByTypeDto (
			String type,
			Double rate
	) {}

	public record CustomMetricElementDto(String name, Object value) {}

	@Schema(description = "${openapi.dto.periodic.description}")
	public record PeriodicUserMetricsDto(
			@Schema(description = "${openapi.dto.periodic.from}")
			ZonedDateTime from,

			@Schema(description = "${openapi.dto.periodic.to}")
			ZonedDateTime to,

			@Schema(description = "${openapi.dto.periodic.label}")
			String label,

			@JsonUnwrapped
			MetricDto metrics
	) {}
}