package com.jiradar.jiradarback.infrastructure.issuetracker.jira.gateway.mapper;

import com.jiradar.jiradarback.core.model.issuetracker.IssueType;
import com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response.JiraIssueTypeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JiraIssueTypeMapper {

	IssueType toDomain(JiraIssueTypeResponseDto issueDto);
}
