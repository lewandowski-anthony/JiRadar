package com.jiradar.jiradarback.infrastructure.jira;

import com.jiradar.jiradarback.core.IssueTrackerService;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.enums.AvailableProviders;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.infrastructure.jira.enums.JiraFieldId;
import com.jiradar.jiradarback.infrastructure.jira.repository.mapper.JiraUserMapper;
import com.jiradar.jiradarback.infrastructure.jira.repository.JiraIssueRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JiraIssueTrackerAdapter implements IssueTrackerService {

	private final JiraServiceClient jiraClient;
	private final JiraUserMapper jiraUserMapper;
	private final JiraIssueRepository jiraIssueRepository;

	@Override
	public boolean supports(String provider) {
		return StringUtils.isNotBlank(provider)
				&& AvailableProviders.JIRA.name().equalsIgnoreCase(provider);
	}

	@Override
	@Cacheable(cacheNames = AvailableCache.JIRA_USER)
	public User getMyself() {
		return jiraUserMapper.toDomainModel(jiraClient.getMyself());
	}

	@Override
	public Issue getIssueByKey(String issueKey){
		JiraIssueResponseDto jiraIssue = jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return jiraIssueRepository.getIssuesForCustomRange(List.of(), LocalDate.now(), LocalDate.now()).stream()
				.filter(issue -> issue.getKey().equals(issueKey))
				.findFirst()
				.orElse(null);
	}

	@Override
	public UserMetrics getMetrics(List<String> projects, DateRange dateRange) {
		if (dateRange == null) {
			throw new IllegalArgumentException("dateRange cannot be null");
		}
		return getMetrics(projects, dateRange.from().toLocalDate(), dateRange.to().toLocalDate());
	}

	@Override
	public UserMetrics getMetrics(List<String> projects, LocalDate startDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(projects)) {
			throw new IllegalArgumentException("projects is empty");
		}

		LocalDate start = startDate != null ? startDate : LocalDate.now().minusDays(30);
		LocalDate end = endDate != null ? endDate : LocalDate.now();

		if (start.plusYears(1).isBefore(end)) {
			throw new IllegalArgumentException("dateRange is more than one year");
		}

		List<Issue> allIssues;

		if (ChronoUnit.DAYS.between(start, end) < 30) {
			allIssues = jiraIssueRepository.getIssuesForCustomRange(projects, start, end);
		} else {
			List<YearMonth> months = getMonthsInInterval(start, end);
			allIssues = new ArrayList<>();
			for (YearMonth month : months) {
				allIssues.addAll(jiraIssueRepository.getIssuesForSpecificMonth(projects, month));
			}
		}

		ZoneId zone = ZoneId.systemDefault();
		DateRange finalRange = new DateRange(start.atStartOfDay(zone), end.plusDays(1).atStartOfDay(zone).minusNanos(1));
		User currentUser = jiraUserMapper.toDomainModel(jiraClient.getMyself());
		return UserMetrics.generate(currentUser, allIssues, finalRange);
	}

	private List<YearMonth> getMonthsInInterval(LocalDate start, LocalDate end) {
		List<YearMonth> months = new ArrayList<>();
		YearMonth current = YearMonth.from(start);
		YearMonth last = YearMonth.from(end);
		while (!current.isAfter(last)) {
			months.add(current);
			current = current.plusMonths(1);
		}
		return months;
	}
}