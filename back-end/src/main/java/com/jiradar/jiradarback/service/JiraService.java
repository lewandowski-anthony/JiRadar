package com.jiradar.jiradarback.service;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.client.jira.dto.request.SearchRequestRequestDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.mapper.JiraIssueMapper;
import com.jiradar.jiradarback.mapper.JiraUserMapper;
import com.jiradar.jiradarback.model.datetime.DateRange;
import com.jiradar.jiradarback.model.jira.JiraIssue;
import com.jiradar.jiradarback.model.jira.JiraUser;
import com.jiradar.jiradarback.model.jira.JiraUserMetrics;
import com.jiradar.jiradarback.model.enums.JiraFieldId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: Clean this mess
@Service
@RequiredArgsConstructor
public class JiraService {

	private final JiraServiceClient jiraClient;
	private final JiraIssueMapper jiraIssueMapper;
	private final JiraUserMapper jiraUserMapper;

	public JiraIssue getIssueByKey(String issueKey){
		JiraIssueResponseDto jiraIssue = jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return jiraIssueMapper.toModel(jiraIssue);
	}

	public JiraUserMetrics getMetrics(String user, List<String> projects) {

		String jqlFormat = "(assignee = \"%1$s\" OR reporter = \"%1$s\" OR status CHANGED BY \"%1$s\") AND project IN (%2$s) AND updated >= -30d";
		List<JiraIssue> jiraIssues =  getJiraIssuesFromJQL(String.format(jqlFormat, user, String.join(",", projects)));

		UserResponseDto userResponseDto = jiraClient.getMyself();
		JiraUser jiraUser = jiraUserMapper.toDomainModel(userResponseDto);

		JiraUserMetrics jiraUserMetric = new JiraMetricsCalculatorService(jiraIssues, new DateRange(ZonedDateTime.now().minusDays(30), ZonedDateTime.now()))
				.calculateForPeriod(jiraUser.getEmail());

		jiraUserMetric.setUser(jiraUser);
		return jiraUserMetric;
	}

	private List<JiraIssue> getJiraIssuesFromJQL(String jql) {
		List<SearchEnvelopeResponseDto> envelopes = new ArrayList<>();
		String nextPageToken = null;
		boolean isLast;

		do {
			SearchEnvelopeResponseDto response = jiraClient.searchTickets(
					SearchRequestRequestDto.builder()
							.jql(jql)
							.expand(JiraFieldId.CHANGELOG.getFieldName())
							.fields(JiraFieldId.getMandatoryFieldNames())
							.maxResults(10)
							.nextPageToken(nextPageToken)
							.build()
			);

			envelopes.add(response);
			nextPageToken = response.getNextPageToken();
			isLast = response.getIsLast();
		} while (!isLast);

		return jiraIssueMapper.toModelList(envelopes);
	}
}
