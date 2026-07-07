package com.jiradar.jiradarback.mapper;

import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.model.jira.JiraUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JiraUserMapper {

	@Mapping(source="emailAddress", target="email")
	@Mapping(source = "displayName", target = "name")
	JiraUser toDomainModel(UserResponseDto jiraResponseDto);
}
