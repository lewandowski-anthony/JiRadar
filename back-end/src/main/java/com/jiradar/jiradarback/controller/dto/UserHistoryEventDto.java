package com.jiradar.jiradarback.controller.dto;

import java.time.ZonedDateTime;

public record UserHistoryEventDto(
    String issueKey,
    String issueSummary,
    String issueType,
	String issueAssignee,
    ZonedDateTime date,
    String transitionType
) {}