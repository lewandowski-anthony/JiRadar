package com.jiradar.jiradarback.model.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JiraUserMetrics {

	private JiraUser user;
	private ZonedDateTime from;
	private ZonedDateTime to;
	private Duration averageCycleTime;
	private Duration averageReviewTime;
	private Long numberOfReviewReopened;
	private Long numberOfIssueDone;
	private Long numberOfIssueStarted;
	private Long numberOfReviewDone;
}
