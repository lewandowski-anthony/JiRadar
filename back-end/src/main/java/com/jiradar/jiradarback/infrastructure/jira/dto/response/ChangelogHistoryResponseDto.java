package com.jiradar.jiradarback.infrastructure.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogHistoryResponseDto {
    private String id;
	private Long created;;
    private UserResponseDto author;
    private List<ChangelogItemResponseDto> items;
}
