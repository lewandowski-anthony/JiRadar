package com.jiradar.jiradarback.infrastructure.issueTracker.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraStatusResponseDto {
    private String id;
    private String name;
    private String iconUrl;
}
