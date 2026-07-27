package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import java.util.List;

public record BulkChangelogResponseDto(
		List<JiraChangelogResponseDto> issueChangeLogs
) {
}