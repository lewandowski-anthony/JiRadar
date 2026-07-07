package com.jiradar.jiradarback.mapper;

import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.model.jira.JiraIssue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { JiraChangelogMapper.class, JiraUserMapper.class })
public interface JiraIssueMapper {

	default List<JiraIssue> toModelList(List<SearchEnvelopeResponseDto> envelopes) {
		if (envelopes == null) return null;

		return envelopes.stream()
				.filter(env -> env != null && env.getIssues() != null)
				.flatMap(env -> env.getIssues().stream())
				.map(this::toModel)
				.collect(Collectors.toList());
	}

    @Mapping(target = "key", source = "issueDto.key")
    @Mapping(target = "projectKey", source = "issueDto.fields.project.key")
    @Mapping(target = "assignee", source = "issueDto.fields.assignee")
    @Mapping(target = "summary", source = "issueDto.fields.summary")
	@Mapping(target = "changes", source = "issueDto.changelog")
	@Mapping(target = "jiraStatus", source = "issueDto.fields.status")
    JiraIssue toModel(JiraIssueResponseDto issueDto);
}