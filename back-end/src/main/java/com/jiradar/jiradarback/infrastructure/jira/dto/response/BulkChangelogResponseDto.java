package com.jiradar.jiradarback.infrastructure.jira.dto.response;

import java.util.List;

public record BulkChangelogResponseDto(
		List<JiraChangelogResponseDto> issueChangeLogs
) {
}