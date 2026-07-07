package com.jiradar.jiradarback.client.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueFieldsResponseDto {
    private String summary;
    private java.time.ZonedDateTime created;
    private java.time.ZonedDateTime updated;
    private Long timespent;
    private Long timeoriginalestimate;
    private Double storyPointEstimate;
    private JiraStatusResponseDto status;
    private JiraProjectResponseDto project;
    private UserResponseDto assignee;
    private JiraIssueTypeResponseDto issuetype;
}
