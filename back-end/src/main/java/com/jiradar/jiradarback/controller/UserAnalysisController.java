package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.DeveloperAnalystResultDto;
import com.jiradar.jiradarback.controller.mapper.DeveloperAnalystResultDtoMapper;
import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.service.DeveloperAnalysisUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Tag(name = "AI Analysis Management", description = "${openapi.endpoint.ai.analysis.tag.description}")
@ConditionalOnProperty(name = "jiradar.feature.flags.ai-analysis.enabled", havingValue = "true")
class UserAnalysisController {

	private final IssueTrackerFactory issueTrackerFactory;
	private final DeveloperAnalysisUseCase developerAnalysisService;
	private final DeveloperAnalystResultDtoMapper developerAnalystResultDtoMapper;

	@GetMapping
	@Operation(summary = "${openapi.endpoint.ai.analysis.summary}", description = "${openapi.endpoint.ai.analysis.description}")
	public Optional<DeveloperAnalystResultDto> getDeveloperAnalysis(
			@Parameter(description = "${openapi.endpoint.user.param.tracker}")
			@PathVariable("issueTracker") String issueTracker,

			@Parameter(description = "${openapi.endpoint.ai.analysis.param.projectKeys}")
			@RequestParam List<String> projectKeys,

			@Parameter(description = "${openapi.endpoint.ai.analysis.param.historyGranularity}")
			@RequestParam String historyGranularity) {

		TimeGranularity timeGranularity = TimeGranularity.valueOf(historyGranularity);
		User currentUser = issueTrackerFactory.getService(issueTracker).getMyself();
		UserMetrics userMetrics = issueTrackerFactory.getService(issueTracker)
				.getMetrics(ProjectSearchParamCommand.fromGranularity(projectKeys, timeGranularity, 1));

		return developerAnalysisService.getUserAnalyse(currentUser, userMetrics, timeGranularity)
				.map(developerAnalystResultDtoMapper::toDto);
	}
}