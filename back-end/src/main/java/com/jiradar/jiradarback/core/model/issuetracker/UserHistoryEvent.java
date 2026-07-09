package com.jiradar.jiradarback.core.model.issuetracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserHistoryEvent {

	private String issueKey;
	private String issueSummary;
	private String issueType;
	private String issueAssignee;
	private ZonedDateTime date;
	private String transitionType;
}