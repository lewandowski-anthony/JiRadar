package com.jiradar.jiradarback.infrastructure.jira.repository;

import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.jira.JiraServiceClient;
import com.jiradar.jiradarback.infrastructure.jira.dto.request.BulkChangelogRequestDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.request.SearchRequestRequestDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraChangelogResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.enums.JiraFieldId;
import com.jiradar.jiradarback.infrastructure.jira.repository.mapper.JiraIssueMapper;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.jiradar.jiradarback.core.constant.DateConstant.DATE_TIME_MINUTE_PATTERN;
import static java.time.ZoneOffset.UTC;

@Component
@RequiredArgsConstructor
public class JiraIssueRepository {

    private final JiraServiceClient jiraClient;
    private final JiraIssueMapper issueMapper;

    @Cacheable(cacheNames = AvailableCache.JIRA_METRICS, key = "{#projects, #month}")
    public List<Issue> getIssuesForSpecificMonth(List<String> projects, YearMonth month) {
        ZonedDateTime start = month.atDay(1).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = month.atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusNanos(1);

        return getJiraIssuesFromRange(projects, start, end);
    }

    @Cacheable(cacheNames = AvailableCache.JIRA_METRICS, key = "{#projects, #startDate, #endDate}")
    public List<Issue> getIssuesForCustomRange(List<String> projects, LocalDate startDate, LocalDate endDate) {
       	ZonedDateTime start = startDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1);

        return getJiraIssuesFromRange(projects, start, end);
    }

    private List<Issue> getJiraIssuesFromRange(List<String> projects, ZonedDateTime start, ZonedDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_MINUTE_PATTERN);
        String jql = String.format("project IN (%s) AND updated >= \"%s\" AND updated <= \"%s\"",
                String.join(",", projects), start.format(formatter), end.format(formatter));

        return getJiraIssuesFromJQL(jql);
    }

    private List<Issue> getJiraIssuesFromJQL(String jql) {
        List<SearchEnvelopeResponseDto> envelopes = new ArrayList<>();
        String nextPageToken = null;
        SearchEnvelopeResponseDto jiraIssues;

        do {
            jiraIssues = jiraClient.searchTickets(
                SearchRequestRequestDto.builder()
                    .jql(jql)
                    .fields(JiraFieldId.getMandatoryFieldNames())
                    .maxResults(100)
                    .nextPageToken(nextPageToken)
                    .build()
            );
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
            if (changeLog != null) {
                jiraIssue.setChangelog(changeLog);
            }
            return changeLog == null;
        }));

        return issueMapper.toModelList(envelopes);
    }

    private Map<String, JiraChangelogResponseDto> getChangeLogsByJiraIds(List<String> issueIds) {
        List<List<String>> chunks = ListUtils.partition(issueIds, 10);

        return chunks.stream()
                .map(chunk -> jiraClient.bulkFetchChangelogs(
                    BulkChangelogRequestDto.builder()
                        .issueIdsOrKeys(chunk)
                        .fieldIds(List.of("status"))
                        .build()
                ))
                .flatMap(bulk -> bulk.issueChangeLogs().stream())
                .collect(Collectors.toMap(
                    JiraChangelogResponseDto::getIssueId,
                    Function.identity(),
                    (existing, _) -> existing
                ));
    }
}