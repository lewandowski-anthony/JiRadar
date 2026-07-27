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
public class SearchEnvelopeResponseDto {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;
    private List<JiraIssueResponseDto> issues;
	private String nextPageToken;
	private Boolean isLast;
}
