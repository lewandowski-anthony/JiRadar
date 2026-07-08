package com.jiradar.jiradarback.controller.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

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
		Double parallelJiraInProgressRate,
		List<UserJiraByTypeDto> jiraRateByType
)
{

	public record UserJiraByTypeDto(
			String jiraType,
			Double rate
	) {}

}
