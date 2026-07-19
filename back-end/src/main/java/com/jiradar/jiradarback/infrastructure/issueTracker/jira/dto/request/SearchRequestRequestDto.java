package com.jiradar.jiradarback.infrastructure.issueTracker.jira.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequestRequestDto {
    private String jql;
	private String expand;
	private List<String> fields;
	private String nextPageToken;
    private Integer maxResults;
}
