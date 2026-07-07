package com.jiradar.jiradarback.client.jira.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BulkChangelogResponseDto(
		List<JiraChangelogResponseDto> issueChangeLogs
) {
}