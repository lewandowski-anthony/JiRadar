package com.jiradar.jiradarback.mapper;

import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueTypeResponseDto;
import com.jiradar.jiradarback.model.issuetracker.IssueType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JiraIssueTypeMapper {

	IssueType toDomain(JiraIssueTypeResponseDto issueDto);
}
