package com.jiradar.jiradarback.infrastructure.jira.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record BulkChangelogRequestDto(
    List<String> issueIdsOrKeys,
    List<String> fieldIds
) {}