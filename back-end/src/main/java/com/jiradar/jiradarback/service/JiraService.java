package com.jiradar.jiradarback.service;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraStatusResponseDto;
import com.jiradar.jiradarback.mapper.JiraIssueMapper;
import com.jiradar.jiradarback.model.JiraIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JiraService {

	private final JiraServiceClient jiraClient;
	private final JiraIssueMapper jiraIssueMapper;

	public JiraIssue getIssueByKey(String issueKey){
		JiraIssueResponseDto jiraIssue = jiraClient.getIssue(issueKey, "changelog");
		JiraStatusResponseDto jiraStatusResponseDto = jiraClient.getStatus(jiraIssue.getFields().getStatus().getId());
		return jiraIssueMapper.toModel(jiraIssue, jiraStatusResponseDto);
	}
}
