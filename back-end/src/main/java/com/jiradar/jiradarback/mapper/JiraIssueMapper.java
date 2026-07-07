package com.jiradar.jiradarback.mapper;

import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraStatusResponseDto;
import com.jiradar.jiradarback.model.JiraIssue;
import com.jiradar.jiradarback.model.JiraStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JiraIssueMapper {

    @Mapping(target = "key", source = "issueDto.key")
    @Mapping(target = "projectKey", source = "issueDto.fields.project.key")
    @Mapping(target = "assignee", source = "issueDto.fields.assignee.displayName")
    @Mapping(target = "summary", source = "issueDto.fields.summary")
    @Mapping(target = "jiraStatus", source = "statusDto")
    JiraIssue toModel(JiraIssueResponseDto issueDto, JiraStatusResponseDto statusDto);

    @Mapping(target = "id", source = "statusDto.id")
    @Mapping(target = "name", source = "statusDto.name")
    @Mapping(target = "iconUrl", source = "statusDto.iconUrl")
    JiraStatus toJiraStatus(JiraStatusResponseDto statusDto);
}