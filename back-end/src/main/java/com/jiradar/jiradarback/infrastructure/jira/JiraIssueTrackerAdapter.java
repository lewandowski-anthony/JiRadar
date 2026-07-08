package com.jiradar.jiradarback.infrastructure.jira;

import com.jiradar.jiradarback.core.IssueTrackerService;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.enums.AvailableProviders;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.jira.dto.request.BulkChangelogRequestDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.request.SearchRequestRequestDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.BulkChangelogResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraChangelogResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.enums.JiraFieldId;
import com.jiradar.jiradarback.infrastructure.jira.mapper.JiraIssueMapper;
import com.jiradar.jiradarback.infrastructure.jira.mapper.JiraUserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JiraIssueTrackerAdapter implements IssueTrackerService {

	private final JiraServiceClient jiraClient;
	private final JiraIssueMapper issueMapper;
	private final JiraUserMapper jiraUserMapper;

	@Override
	public boolean supports(String provider) {
		return StringUtils.isNotBlank(provider)
				&& AvailableProviders.JIRA.name().equalsIgnoreCase(provider);
	}

	@Override
	public User getMyself() {
		return jiraUserMapper.toDomainModel(jiraClient.getMyself());
	}

	@Override
	public Issue getIssueByKey(String issueKey){
		JiraIssueResponseDto jiraIssue = jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return issueMapper.toModel(jiraIssue);
	}

	@Override
	public UserMetrics getMetrics(List<String> projects) {
		if (CollectionUtils.isEmpty(projects)) {
			throw new IllegalArgumentException("projects is empty");
		}

		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(
					() -> jiraUserMapper.toDomainModel(jiraClient.getMyself()), executor);

			String jqlFormat = "project IN (%1$s) AND updated >= -30d";
			List<Issue> issues = getJiraIssuesFromJQL(String.format(jqlFormat, String.join(",", projects)), executor);

			User jiraUser = userFuture.join();

			return UserMetrics.generate(jiraUser, issues, new DateRange(ZonedDateTime.now().minusDays(30), ZonedDateTime.now()));
		}
	}

	private List<Issue> getJiraIssuesFromJQL(String jql, ExecutorService executor) {
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

		Map<String, JiraChangelogResponseDto> changelogResponseDtoMap = getChangeLogsByJiraIds(jiraIds, executor);

		envelopes.forEach(envelope -> envelope.getIssues().removeIf(jiraIssue -> {
			JiraChangelogResponseDto changeLog = changelogResponseDtoMap.get(jiraIssue.getId());
			if (changeLog != null)
				jiraIssue.setChangelog(changeLog);
			return changeLog == null;
		}));

		return issueMapper.toModelList(envelopes);
	}

	private SearchEnvelopeResponseDto getIssueFromJiraPage(String jql, String token) {
		return jiraClient.searchTickets(
				SearchRequestRequestDto.builder()
						.jql(jql)
						.fields(JiraFieldId.getMandatoryFieldNames())
						.maxResults(100)
						.nextPageToken(token)
						.build()
		);
	}

	private Map<String, JiraChangelogResponseDto> getChangeLogsByJiraIds(List<String> issueIds, ExecutorService executor) {
		List<List<String>> chunks = ListUtils.partition(issueIds, 5);

		List<CompletableFuture<BulkChangelogResponseDto>> futures = chunks.stream()
				.map(chunk -> CompletableFuture.supplyAsync(() -> jiraClient.bulkFetchChangelogs(
						BulkChangelogRequestDto.builder()
								.issueIdsOrKeys(chunk)
								.fieldIds(List.of("status"))
								.build()
				), executor))
				.toList();

		return futures.stream()
				.map(CompletableFuture::join)
				.flatMap(bulk -> bulk.issueChangeLogs().stream())
				.collect(Collectors.toMap(
						JiraChangelogResponseDto::getIssueId,
						Function.identity(),
						(existing, replacement) -> existing
				));
	}
}