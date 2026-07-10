package com.jiradar.jiradarback.controller.dto;

public record IssueDto(
		String key,
		String projectKey,
		UserDto assignee,
		String summary,
		String type
) {

}
