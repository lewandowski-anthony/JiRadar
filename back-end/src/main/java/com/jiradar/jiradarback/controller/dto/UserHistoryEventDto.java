package com.jiradar.jiradarback.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;

@Schema(description = "${openapi.dto.history.description}")
public record UserHistoryEventDto(
		@Schema(description = "${openapi.dto.history.issueKey}", example = "TRACK-432")
		String issueKey,

		@Schema(description = "${openapi.dto.history.issueSummary}", example = "Implement OAuth2 authentication layer")
		String issueSummary,

		@Schema(description = "${openapi.dto.history.issueType}", example = "Feature")
		String issueType,

		@Schema(description = "${openapi.dto.history.date}")
		ZonedDateTime date,

		@Schema(description = "${openapi.dto.history.transitionType}", example = "In Progress -> Code Review")
		String transitionType,

		@Schema(description = "${openapi.dto.history.issueAssignee}")
		UserDto issueAssignee
) {}