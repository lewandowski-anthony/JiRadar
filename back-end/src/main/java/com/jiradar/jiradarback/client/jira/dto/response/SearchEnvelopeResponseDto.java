package com.jiradar.jiradarback.client.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchEnvelopeResponseDto {
    private Integer startAt;
    private Integer maxResults;
    private Integer total;
    private java.util.List<JiraIssueResponseDto> issues;
}
