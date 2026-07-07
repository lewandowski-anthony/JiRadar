package com.jiradar.jiradarback.client.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
