package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.UserDto;
import com.jiradar.jiradarback.controller.dto.UserHistoryEventDto;
import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.controller.mapper.UserDtoMapper;
import com.jiradar.jiradarback.controller.mapper.UserHistoryEventDtoMapper;
import com.jiradar.jiradarback.controller.mapper.UserMetricsDtoMapper;
import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.service.DeveloperAnalysisUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/users")
@Tag(name = "User Metrics", description = "${openapi.endpoint.user.tag.description}")
class UserMetricsController {

	private final UserMetricsDtoMapper userMetricsDtoMapper;
	private final DeveloperAnalysisUseCase developerAnalysisService;
	private final IssueTrackerFactory issueTrackerFactory;

	@GetMapping("/me/metrics")
	@Operation(summary = "${openapi.endpoint.user.metrics.summary}", description = "${openapi.endpoint.user.metrics.description}")
	public UserMetricsDto getDeveloperPerformance(
			@PathVariable("issueTracker") String issueTracker,
			@RequestParam List<String> projectKeys,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) String historyGranularity) {

		return userMetricsDtoMapper.mapToDto(
				issueTrackerFactory.getService(issueTracker).getMetrics(
						new ProjectSearchParamCommand(projectKeys, startDate, endDate), TimeGranularity.fromString(historyGranularity)
				)
		);
	}

	@GetMapping("/me/metrics/analysis")
	@Operation(summary = "${openapi.endpoint.user.metrics.summary}", description = "${openapi.endpoint.user.metrics.description}")
	public Optional<DeveloperAnalystResult> getDeveloperAnalysis(

			@PathVariable("issueTracker") String issueTracker,
			@RequestParam List<String> projectKeys,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) String historyGranularity) {

		UserMetrics userMetrics = issueTrackerFactory.getService(issueTracker).getMetrics(
				new ProjectSearchParamCommand(projectKeys, startDate, endDate), TimeGranularity.fromString(historyGranularity)
		);
		return developerAnalysisService.analyzeDeveloperProfile(userMetrics, "en");
	}
}
