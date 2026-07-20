package com.jiradar.jiradarback.infrastructure.ai.common.model.response;

import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;

import java.util.List;
import java.util.Map;

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
		Map<String, String> keyMetricInterpretations,
		List<String> predictedRisksIfUnchanged
) {}