package com.jiradar.jiradarback.controller.dto;

import com.jiradar.jiradarback.model.jira.JiraUser;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record UserMetricsDto(
		JiraUser user,
		ZonedDateTime from,
		ZonedDateTime to,
		String averageCycleTime,
		String averageReviewTime,
		Long numberOfReviewReopened,
		Long numberOfIssueDone,
	 	Long numberOfIssueStarted,
		Long numberOfReviewDone,
		Double deliverySuccessRate,
		Double teamReviewParticipationRate,
		Double pingPongReviewRate
)
{

}
