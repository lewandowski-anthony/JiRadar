package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jira/users")
public class JiraUserProxyController {

	private final JiraServiceClient jiraServiceClient;

	@GetMapping("/myself")
	public UserResponseDto getMyself() {
		return jiraServiceClient.getMyself();
	}
}
