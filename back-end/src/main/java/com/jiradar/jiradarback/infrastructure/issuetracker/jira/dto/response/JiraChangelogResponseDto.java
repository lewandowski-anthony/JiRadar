package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraChangelogResponseDto {

	private String issueId;
	private Integer startAt;
	private Integer maxResults;
	private Integer total;
	private List<ChangelogHistoryResponseDto> changeHistories;
}
