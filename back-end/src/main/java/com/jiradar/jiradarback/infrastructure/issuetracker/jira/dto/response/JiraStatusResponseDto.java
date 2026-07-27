package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraStatusResponseDto {
    private String id;
    private String name;
    private String iconUrl;
}
