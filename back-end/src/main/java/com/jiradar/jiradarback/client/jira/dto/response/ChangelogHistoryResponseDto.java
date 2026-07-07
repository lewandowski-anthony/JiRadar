package com.jiradar.jiradarback.client.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogHistoryResponseDto {
    private String id;
    private java.time.ZonedDateTime created;
    private UserResponseDto author;
    private java.util.List<ChangelogItemResponseDto> items;
}
