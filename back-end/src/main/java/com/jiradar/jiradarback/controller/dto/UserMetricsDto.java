package com.jiradar.jiradarback.controller.dto;

import com.jiradar.jiradarback.model.jira.JiraUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserMetricsDto {

	private JiraUser user;
	private ZonedDateTime from;
	private ZonedDateTime to;
	private String averageCycleTime;
	private String averageReviewTime;
	private Long numberOfReviewReopened;
	private Long numberOfIssueDone;
	private Long numberOfIssueStarted;
	private Long numberOfReviewDone;
}
