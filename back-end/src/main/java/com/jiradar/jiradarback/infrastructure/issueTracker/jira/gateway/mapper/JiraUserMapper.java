package com.jiradar.jiradarback.infrastructure.issueTracker.jira.gateway.mapper;

import com.jiradar.jiradarback.infrastructure.issueTracker.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JiraUserMapper {

	@Mapping(source="emailAddress", target="email")
	@Mapping(source = "displayName", target = "name")
	User toDomainModel(UserResponseDto jiraResponseDto);
}
