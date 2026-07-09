package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.UserDto;
import com.jiradar.jiradarback.controller.dto.UserHistoryEventDto;
import com.jiradar.jiradarback.controller.mapper.UserDtoMapper;
import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.controller.mapper.UserHistoryEventDtoMapper;
import com.jiradar.jiradarback.controller.mapper.UserMetricsDtoMapper;
import com.jiradar.jiradarback.core.IssueTrackerService;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
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
@RequestMapping("/api/v1/tracker/{issueTracker}/users/myself")
public class UserController {

	private final UserMetricsDtoMapper userMetricsDtoMapper;
	private final UserDtoMapper userDtoMapper;
	private final UserHistoryEventDtoMapper userHistoryEventDtoMapper;

	@GetMapping
	public UserDto getMyself(IssueTrackerService tracker) {
		return userDtoMapper.toDto(tracker.getMyself());
	}

	@GetMapping("/metrics")
	public UserMetricsDto getDeveloperPerformance(
			@RequestParam List<String> projectsKey,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) String historyGranularity,
			IssueTrackerService tracker) {

		return userMetricsDtoMapper.mapToDto(
				tracker.getMetrics(
						new ProjectSearchParamCommand(projectsKey, startDate, endDate), TimeGranularity.fromString(historyGranularity)
				)
		);
	}

	@GetMapping("/history")
	public Page<UserHistoryEventDto> getDeveloperHistory(
			@RequestParam List<String> projectsKey,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@PageableDefault(page = 0, size = 20) Pageable pageable,
			IssueTrackerService tracker) {

		return tracker.getHistory(
				new ProjectSearchParamCommand(projectsKey, startDate, endDate), pageable
		).map(userHistoryEventDtoMapper::toDto);
	}
}
