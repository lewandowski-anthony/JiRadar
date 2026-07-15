package com.jiradar.jiradarback.infrastructure.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssueTypeResponseDto {
    private String id;
    private String name;
    private Boolean subtask;
}
