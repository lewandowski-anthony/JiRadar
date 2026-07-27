package com.jiradar.jiradarback.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "${openapi.dto.ai.analysis.description}")
public record DeveloperAnalystResultDto(

		@Schema(description = "${openapi.dto.ai.analysis.profileSummary}")
		String profileSummary,

		@Schema(description = "${openapi.dto.ai.analysis.qualities}")
		List<String> qualities,

		@Schema(description = "${openapi.dto.ai.analysis.improvements}")
		List<String> improvements,

		@Schema(description = "${openapi.dto.ai.analysis.assignedTitle}")
		String assignedTitle,

		@Schema(description = "${openapi.dto.ai.analysis.technicalVelocityScore}")
		int technicalVelocityScore,

		@Schema(description = "${openapi.dto.ai.analysis.teamCollaborationScore}")
		int teamCollaborationScore,

		@Schema(description = "${openapi.dto.ai.analysis.deliveryReliabilityScore}")
		int deliveryReliabilityScore,

		@Schema(description = "${openapi.dto.ai.analysis.globalAgilityScore}")
		int globalAgilityScore,

		@Schema(description = "${openapi.dto.ai.analysis.primaryBottleneck}")
		String primaryBottleneck,

		@Schema(description = "${openapi.dto.ai.analysis.mainFocusForNextSprint}")
		String mainFocusForNextSprint,

		@Schema(description = "${openapi.dto.ai.analysis.concreteActionPlan}")
		List<String> concreteActionPlan,

		@Schema(description = "${openapi.dto.ai.analysis.longTermGrowthTrack}")
		String longTermGrowthTrack,

		@Schema(description = "${openapi.dto.ai.analysis.peerCoachingRecommendation}")
		String peerCoachingRecommendation,

		@Schema(description = "${openapi.dto.ai.analysis.keyMetricInterpretations}")
		KeyMetricInterpretationsDto keyMetricInterpretations,

		@Schema(description = "${openapi.dto.ai.analysis.predictedRisksIfUnchanged}")
		List<String> predictedRisksIfUnchanged
) {

	@Schema(description = "${openapi.dto.ai.analysis.keyMetrics.description}")
	public record KeyMetricInterpretationsDto(
			@Schema(description = "${openapi.dto.ai.analysis.keyMetrics.throughput}")
			String throughput,

			@Schema(description = "${openapi.dto.ai.analysis.keyMetrics.cycleTime}")
			String cycleTime,

			@Schema(description = "${openapi.dto.ai.analysis.keyMetrics.reviewParticipation}")
			String reviewParticipation
	) {}
}