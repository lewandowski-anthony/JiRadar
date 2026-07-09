package com.jiradar.jiradarback.infrastructure.jira;

import com.jiradar.jiradarback.core.IssueTrackerService;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.enums.AvailableProviders;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.jira.enums.JiraFieldId;
import com.jiradar.jiradarback.infrastructure.jira.repository.mapper.JiraUserMapper;
import com.jiradar.jiradarback.infrastructure.jira.repository.JiraIssueRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jiradar.jiradarback.core.model.enums.TransitionType.OTHER;

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
		jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return jiraIssueRepository.getIssuesForCustomRange(List.of(), LocalDate.now(), LocalDate.now()).stream()
				.filter(issue -> issue.getKey().equals(issueKey))
				.findFirst()
				.orElse(null);
	}

	@Override
	public UserMetrics getMetrics(ProjectSearchParamCommand command, TimeGranularity historyGranularity) {


		List<Issue> allIssues = fetchIssuesForRange(command.projectKeys(), command.startDate(), command.endDate());
		DateRange finalRange = DateRange.from(command.startDate(), command.endDate());
		User currentUser = getMyself();

		return UserMetrics.generate(currentUser, allIssues, finalRange, historyGranularity);
	}

	@Override
	public Page<UserHistoryEvent> getHistory(ProjectSearchParamCommand command, Pageable pageable) {

		List<Issue> allIssues = fetchIssuesForRange(command.projectKeys(), command.startDate(), command.endDate());
		User currentUser = getMyself();
		DateRange finalRange = DateRange.from(command.startDate(), command.endDate());

		List<UserHistoryEvent> allUserEvents = allIssues.stream()
				.flatMap(issue -> issue.getChanges().stream()
						.filter(change ->
									issue.isAuthor((currentUser.getEmail())) ||
									(change.isAuthor((currentUser.getEmail())) && change.isReviewDone())
						)
						.filter(change -> change.getTransitionType() != OTHER)
						.filter(change -> finalRange.contains(change.getDate()))
						.map(change -> new UserHistoryEvent(
								issue.getKey(),
								issue.getSummary(),
								issue.getType().getName(),
								issue.getAssignee() != null ? issue.getAssignee().getEmail() : "Unassigned",
								change.getDate(),
								change.getTransitionType().name()
						))
				)
				.sorted(java.util.Comparator.comparing(UserHistoryEvent::getDate).reversed())
				.toList();

		long startOffset = pageable.getOffset();

		if (startOffset >= allUserEvents.size()) {
			return new PageImpl<>(Collections.emptyList(), pageable, allUserEvents.size());
		}

		List<UserHistoryEvent> pagedContent = allUserEvents.stream()
				.skip(startOffset)
				.limit(pageable.getPageSize())
				.toList();

		return new PageImpl<>(pagedContent, pageable, allUserEvents.size());
	}

	private List<Issue> fetchIssuesForRange(List<String> projectKeys, LocalDate start, LocalDate end) {
		if (ChronoUnit.DAYS.between(start, end) < 30) {
			return jiraIssueRepository.getIssuesForCustomRange(projectKeys, start, end);
		}

		List<YearMonth> months = getMonthsInInterval(start, end);
		List<Issue> allIssues = new ArrayList<>();
		for (YearMonth month : months) {
			allIssues.addAll(jiraIssueRepository.getIssuesForSpecificMonth(projectKeys, month));
		}
		return allIssues;
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