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
public class SearchEnvelopeResponseDto {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;
    private List<JiraIssueResponseDto> issues;
	private String nextPageToken;
	private Boolean isLast;
}
