package com.jiradar.jiradarback.controller.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record UserMetricsDto(

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
		Double pingPongReviewRate,
		Double parallelIssuesInProgressRate,
		List<UserIssueByTypeDto> issueRateByType
)
{

	public record UserIssueByTypeDto(
			String type,
			Double rate
	) {}

}
