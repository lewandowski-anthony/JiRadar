package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.controller.mapper.UserMetricsDtoMapper;
import com.jiradar.jiradarback.model.jira.JiraUserMetrics;
import com.jiradar.jiradarback.service.JiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jira/metrics")
@RequiredArgsConstructor
public class JiraMetricsController {

	private final JiraService jiraService;
	private final JiraServiceClient jiraServiceClient;
	private final UserMetricsDtoMapper userMetricsDtoMapper;

	@GetMapping("/myself")
	public UserMetricsDto getDeveloperPerformance(){

		String userMail = jiraServiceClient.getMyself().getEmailAddress();
		return userMetricsDtoMapper.mapToDto(jiraService.getMetrics(userMail, List.of("SMSUP")));
	}
}
