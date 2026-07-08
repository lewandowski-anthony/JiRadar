package com.jiradar.jiradarback.infrastructure.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssueResponseDto {
    private String id;
    private String key;
    private String self;
    private IssueFieldsResponseDto fields;
    private JiraChangelogResponseDto changelog;
}
