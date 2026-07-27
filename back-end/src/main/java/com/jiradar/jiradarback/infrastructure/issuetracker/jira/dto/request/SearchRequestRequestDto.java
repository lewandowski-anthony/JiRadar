package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
