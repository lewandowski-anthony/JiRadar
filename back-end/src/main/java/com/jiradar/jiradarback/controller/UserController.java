package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.UserDto;
import com.jiradar.jiradarback.controller.dto.UserHistoryEventDto;
import com.jiradar.jiradarback.controller.mapper.UserDtoMapper;
import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.controller.mapper.UserHistoryEventDtoMapper;
import com.jiradar.jiradarback.controller.mapper.UserMetricsDtoMapper;
import org.springframework.web.bind.annotation.PathVariable;
import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/users")
@Tag(name = "User Management", description = "${openapi.endpoint.user.tag.description}")
public class UserController {

	private final UserMetricsDtoMapper userMetricsDtoMapper;
	private final UserDtoMapper userDtoMapper;
	private final UserHistoryEventDtoMapper userHistoryEventDtoMapper;
	private final IssueTrackerFactory issueTrackerFactory;

	@GetMapping("/me")
	@Operation(summary = "${openapi.endpoint.user.me.summary}", description = "${openapi.endpoint.user.me.description}")
	public UserDto getMyself(@PathVariable("issueTracker") String issueTracker) {
		return userDtoMapper.toDto(issueTrackerFactory.getService(issueTracker).getMyself());
	}

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

	@GetMapping("/me/history")
	@Operation(summary = "${openapi.endpoint.user.history.summary}", description = "${openapi.endpoint.user.history.description}")
	public Page<UserHistoryEventDto> getDeveloperHistory(
			@RequestParam List<String> projectsKey,
			@PathVariable("issueTracker") String issueTracker,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@PageableDefault(page = 0, size = 20) Pageable pageable) {

		return issueTrackerFactory.getService(issueTracker).getHistory(
				new ProjectSearchParamCommand(projectsKey, startDate, endDate), pageable
		).map(userHistoryEventDtoMapper::toDto);
	}
}
