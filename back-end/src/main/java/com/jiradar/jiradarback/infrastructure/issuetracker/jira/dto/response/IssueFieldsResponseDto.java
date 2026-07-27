package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueFieldsResponseDto {
    private String summary;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private Long timeSpent;
    private Long originalTimeEstimate;
    private Double storyPointEstimate;
    private JiraStatusResponseDto status;
    private JiraProjectResponseDto project;
    private UserResponseDto assignee;
    @JsonProperty("issuetype")
	private JiraIssueTypeResponseDto issueType;
}
