package com.jiradar.jiradarback.mapper;

import com.jiradar.jiradarback.client.jira.dto.response.ChangelogItemResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraChangelogResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.model.jira.JiraChangeLog;
import com.jiradar.jiradarback.model.enums.JiraFieldId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JiraUserMapper.class)
public interface JiraChangelogMapper {

	default List<JiraChangeLog> toModelList(JiraChangelogResponseDto changelogResponseDto) {
		if (changelogResponseDto == null || changelogResponseDto.getHistories() == null) {
			return Collections.emptyList();
		}

		return changelogResponseDto.getHistories().stream()
				.filter(history -> history.getItems() != null)
				.flatMap(history -> history.getItems().stream()
						.map(item -> toJiraChangeLog(item, history.getCreated(), history.getAuthor())))
				.collect(Collectors.toList());
	}

	@Mapping(target = "date", source = "createdDate")
	@Mapping(target = "field", source = "itemDto.field")
	@Mapping(target = "fieldType", source = "itemDto.field", qualifiedByName = "stringToJiraFieldId")
	@Mapping(target = "previousValue", source = "itemDto.fromString")
	@Mapping(target = "newValue", source = "itemDto.toString")
	@Mapping(target = "author", source = "userResponseDto")
	JiraChangeLog toJiraChangeLog(ChangelogItemResponseDto itemDto, ZonedDateTime createdDate, UserResponseDto userResponseDto);

	@Named("stringToJiraFieldId")
	default JiraFieldId stringToJiraFieldId(String field) {
		if (field == null) {
			return null;
		}
		try {
			return JiraFieldId.valueOf(field.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}