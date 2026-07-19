package com.jiradar.jiradarback.infrastructure.issueTracker.jira.gateway.mapper;

import com.jiradar.jiradarback.infrastructure.issueTracker.jira.dto.response.JiraIssueTypeResponseDto;
import com.jiradar.jiradarback.core.model.issuetracker.IssueType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JiraIssueTypeMapper {

	IssueType toDomain(JiraIssueTypeResponseDto issueDto);
}
