package com.jiradar.jiradarback.infrastructure.issueTracker.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogItemResponseDto {
    private String field;
    private String from;
    private String fromString;
    private String to;
    private String toString;
}
