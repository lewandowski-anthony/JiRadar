package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.controller.mapper.UserMetricsDtoMapper;
import com.jiradar.jiradarback.client.service.JiraService;
import com.jiradar.jiradarback.service.IssueTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jira/users/myself")
public class JiraUserProxyController {

	private final JiraServiceClient jiraServiceClient;
	private final IssueTrackerService jiraService;
	private final UserMetricsDtoMapper userMetricsDtoMapper;

	@GetMapping
	public UserResponseDto getMyself() {
		return jiraServiceClient.getMyself();
	}

	@GetMapping("/metrics")
	public UserMetricsDto getDeveloperPerformance(@RequestParam List<String> projectsKey){
		return userMetricsDtoMapper.mapToDto(jiraService.getMetrics(projectsKey));
	}
}
