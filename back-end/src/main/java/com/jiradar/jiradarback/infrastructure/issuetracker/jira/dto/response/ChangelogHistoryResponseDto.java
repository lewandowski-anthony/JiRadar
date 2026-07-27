package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogHistoryResponseDto {
    private String id;
	private Long created;
    private UserResponseDto author;
    private List<ChangelogItemResponseDto> items;
}
