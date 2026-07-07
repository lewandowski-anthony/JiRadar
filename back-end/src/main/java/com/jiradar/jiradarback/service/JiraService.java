package com.jiradar.jiradarback.service;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import com.jiradar.jiradarback.client.jira.dto.request.BulkChangelogRequestDto;
import com.jiradar.jiradarback.client.jira.dto.request.SearchRequestRequestDto;
import com.jiradar.jiradarback.client.jira.dto.response.BulkChangelogResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraChangelogResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import com.jiradar.jiradarback.mapper.JiraChangelogMapper;
import com.jiradar.jiradarback.mapper.JiraIssueMapper;
import com.jiradar.jiradarback.mapper.JiraUserMapper;
import com.jiradar.jiradarback.model.datetime.DateRange;
import com.jiradar.jiradarback.model.jira.JiraIssue;
import com.jiradar.jiradarback.model.jira.JiraUser;
import com.jiradar.jiradarback.model.jira.JiraUserMetrics;
import com.jiradar.jiradarback.model.enums.JiraFieldId;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: Clean this mess
@Service
@RequiredArgsConstructor
public class JiraService {

	private final JiraServiceClient jiraClient;
	private final JiraIssueMapper jiraIssueMapper;
	private final JiraUserMapper jiraUserMapper;
	private final JiraChangelogMapper jiraChangelogMapper;

	public JiraIssue getIssueByKey(String issueKey){
		JiraIssueResponseDto jiraIssue = jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return jiraIssueMapper.toModel(jiraIssue);
	}

	public JiraUserMetrics getMetrics(String user, List<String> projects) {

		String jqlFormat = "project IN (%1$s) AND updated >= -30d";

		List<JiraIssue> jiraIssues =  getJiraIssuesFromJQL(String.format(jqlFormat, String.join(",", projects)));

		UserResponseDto userResponseDto = jiraClient.getMyself();
		JiraUser jiraUser = jiraUserMapper.toDomainModel(userResponseDto);

		return JiraUserMetrics.generate(jiraUser, jiraIssues, new DateRange(ZonedDateTime.now().minusDays(30), ZonedDateTime.now()));
	}

	private List<JiraIssue> getJiraIssuesFromJQL(String jql) {
		List<SearchEnvelopeResponseDto> envelopes = new ArrayList<>();
		String nextPageToken = null;
		SearchEnvelopeResponseDto jiraIssues;

		do {
			jiraIssues = getIssueFromJiraPage(jql, nextPageToken);
			envelopes.add(jiraIssues);
			nextPageToken = jiraIssues.getNextPageToken();
		} while (!jiraIssues.getIsLast());

		List<String> jiraIds = envelopes.stream()
				.flatMap(envelope -> envelope.getIssues().stream())
				.map(JiraIssueResponseDto::getId)
				.toList();

		Map<String, JiraChangelogResponseDto> changelogResponseDtoMap = getChangeLogsByJiraIds(jiraIds);

		envelopes.forEach(envelope -> envelope.getIssues().removeIf(jiraIssue -> {
			JiraChangelogResponseDto changeLog = changelogResponseDtoMap.get(jiraIssue.getId());
			if (changeLog != null)
				jiraIssue.setChangelog(changeLog);
			return changeLog == null;
		}));

		return jiraIssueMapper.toModelList(envelopes);
	}

	private SearchEnvelopeResponseDto getIssueFromJiraPage(String jql, String token) {
		return jiraClient.searchTickets(
				SearchRequestRequestDto.builder()
						.jql(jql)
						.fields(JiraFieldId.getMandatoryFieldNames())
						.maxResults(50)
						.nextPageToken(token)
						.build()
		);
	}

	private Map<String, JiraChangelogResponseDto> getChangeLogsByJiraIds(List<String> issueIds) {

		List<List<String>> chunks = ListUtils.partition(issueIds, 5);

		try(ExecutorService httpExecutor = Executors.newFixedThreadPool(20)) {
			return chunks.stream()
					.map(chunk -> CompletableFuture.supplyAsync(() -> jiraClient.bulkFetchChangelogs(
							BulkChangelogRequestDto.builder()
									.issueIdsOrKeys(chunk)
									.fieldIds(List.of("status"))
									.build()
					), httpExecutor))
					.map(CompletableFuture::join)
					.flatMap(bulk -> bulk.issueChangeLogs().stream())
					.collect(Collectors.toMap(
							JiraChangelogResponseDto::getIssueId,
							Function.identity(),
							(existing, replacement) -> existing
					));
		}
	}
}
