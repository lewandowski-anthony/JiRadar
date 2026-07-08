package com.jiradar.jiradarback.infrastructure.jira.mapper;

import com.jiradar.jiradarback.infrastructure.jira.config.JiraClientConfig;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.ChangelogItemResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraChangelogResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.core.model.enums.TransitionType;
import com.jiradar.jiradarback.core.model.issuetracker.ChangeLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { JiraUserMapper.class, JiraTransitionResolver.class })
public interface JiraChangeLogMapper {

	default List<ChangeLog> toModelList(JiraChangelogResponseDto changelogResponseDto) {

		if (changelogResponseDto == null || changelogResponseDto.getChangeHistories() == null) {
			return Collections.emptyList();
		}

		return changelogResponseDto.getChangeHistories().stream()
				.filter(history -> history.getItems() != null)
				.flatMap(history -> history.getItems().stream()
						.map(item -> toChangeLog(item, history.getCreated(), history.getAuthor())))
				.collect(Collectors.toList());
	}

	@Mapping(target = "author", source = "userResponseDto")
	@Mapping(target = "date", source = "createdDate", qualifiedByName = "mapMillisToZonedDateTime")
	@Mapping(target = "transitionType", source = "itemDto")
	ChangeLog toChangeLog(ChangelogItemResponseDto itemDto, long createdDate, UserResponseDto userResponseDto);

	@Named("mapMillisToZonedDateTime")
	default ZonedDateTime mapMillisToZonedDateTime(Long millis) {
		if (millis == null) return null;
		return java.time.Instant.ofEpochMilli(millis)
				.atZone(java.time.ZoneId.systemDefault());
	}
}