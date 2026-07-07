package com.jiradar.jiradarback.client.jira.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestRequestDto {
    private String jql;
    private Integer startAt;
    private Integer maxResults;
    private java.util.List<Object> expand;
}
