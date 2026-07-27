package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.UserDto;
import com.jiradar.jiradarback.controller.mapper.UserDtoMapper;
import org.springframework.web.bind.annotation.PathVariable;
import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/users/me")
@Tag(name = "User Management", description = "${openapi.endpoint.user.tag.description}")
public class UserController {

	private final UserDtoMapper userDtoMapper;
	private final IssueTrackerFactory issueTrackerFactory;

	@GetMapping
	@Operation(summary = "${openapi.endpoint.user.me.summary}", description = "${openapi.endpoint.user.me.description}")
	public UserDto getMyself(@PathVariable("issueTracker") String issueTracker) {
		return userDtoMapper.toDto(issueTrackerFactory.getService(issueTracker).getMyself());
	}

}
