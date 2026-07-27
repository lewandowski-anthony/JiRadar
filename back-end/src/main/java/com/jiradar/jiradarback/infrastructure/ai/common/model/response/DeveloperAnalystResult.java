package com.jiradar.jiradarback.infrastructure.ai.common.model.response;

import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;

import java.util.List;

public record DeveloperAnalystResult(
		String profileSummary,
		List<String> qualities,
		List<String> improvements,
		DeveloperTitle assignedTitle,
		int technicalVelocityScore,
		int teamCollaborationScore,
		int deliveryReliabilityScore,
		int globalAgilityScore,
		String primaryBottleneck,
		String mainFocusForNextSprint,
		List<String> concreteActionPlan,
		String longTermGrowthTrack,
		String peerCoachingRecommendation,
		KeyMetricInterpretations keyMetricInterpretations,
		List<String> predictedRisksIfUnchanged
) {

	public record KeyMetricInterpretations(
			String throughput,
			String cycleTime,
			String reviewParticipation
	) {}
}