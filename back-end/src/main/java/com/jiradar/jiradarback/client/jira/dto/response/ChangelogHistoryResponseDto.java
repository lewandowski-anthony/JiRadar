package com.jiradar.jiradarback.client.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogHistoryResponseDto {
    private String id;
    private ZonedDateTime created;
    private UserResponseDto author;
    private List<ChangelogItemResponseDto> items;
}
