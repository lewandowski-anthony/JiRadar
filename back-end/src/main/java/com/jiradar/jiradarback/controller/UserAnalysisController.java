package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.service.DeveloperAnalysisUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/users/me/analysis")
@Tag(name = "User Management", description = "${openapi.endpoint.user.tag.description}")
class UserAnalysisController {

	private final IssueTrackerFactory issueTrackerFactory;
	private final DeveloperAnalysisUseCase developerAnalysisService;

	@GetMapping
	@Operation(summary = "${openapi.endpoint.user.metrics.analysis.summary}", description = "${openapi.endpoint.user.metrics.analysis.description}")
	public Optional<DeveloperAnalystResult> getDeveloperAnalysis(
			@PathVariable("issueTracker") String issueTracker,
			@RequestParam List<String> projectKeys,
			@RequestParam String historyGranularity) {

		TimeGranularity timeGranularity = TimeGranularity.valueOf(historyGranularity);
		User currentUser = issueTrackerFactory.getService(issueTracker).getMyself();
		UserMetrics userMetrics = issueTrackerFactory.getService(issueTracker)
				.getMetrics(ProjectSearchParamCommand.fromGranularity(projectKeys, timeGranularity, 1), timeGranularity);

		return developerAnalysisService.getUserAnalyse(currentUser, userMetrics, timeGranularity);
	}
}
