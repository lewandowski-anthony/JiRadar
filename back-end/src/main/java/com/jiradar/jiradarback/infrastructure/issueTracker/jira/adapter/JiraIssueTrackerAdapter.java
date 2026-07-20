package com.jiradar.jiradarback.infrastructure.issueTracker.jira.adapter;

import com.jiradar.jiradarback.common.config.CustomMetricsProperties;
import com.jiradar.jiradarback.core.mapper.UserHistoryMapper;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.enums.AvailableProviders;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.core.service.AbstractIssueTrackerService;
import com.jiradar.jiradarback.core.util.PageUtils;
import com.jiradar.jiradarback.exception.BusinessException;
import com.jiradar.jiradarback.infrastructure.issueTracker.jira.JiraServiceClient;
import com.jiradar.jiradarback.infrastructure.issueTracker.jira.enums.JiraFieldId;
import com.jiradar.jiradarback.infrastructure.issueTracker.jira.gateway.mapper.JiraUserMapper;
import com.jiradar.jiradarback.infrastructure.issueTracker.jira.gateway.JiraTrackerGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.jiradar.jiradarback.core.model.enums.TransitionType.OTHER;

@Service
public class JiraIssueTrackerAdapter extends AbstractIssueTrackerService {

	private final JiraServiceClient jiraClient;
	private final JiraUserMapper jiraUserMapper;
	private final JiraTrackerGateway jiraIssueRepository;

	public JiraIssueTrackerAdapter(
			CustomMetricsProperties customMetricsProperties,
			JiraServiceClient jiraClient,
			JiraUserMapper jiraUserMapper,
			UserHistoryMapper userHistoryMapper,
			JiraTrackerGateway jiraIssueRepository) {

		super(customMetricsProperties, userHistoryMapper);
		this.jiraClient = jiraClient;
		this.jiraUserMapper = jiraUserMapper;
		this.jiraIssueRepository = jiraIssueRepository;
	}

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
	public Optional<Issue> getIssueByKey(String issueKey) {
		jiraClient.getIssue(issueKey, JiraFieldId.CHANGELOG.name());
		return jiraIssueRepository.getIssuesForCustomRange(List.of(), LocalDate.now(ZoneId.systemDefault()), LocalDate.now(ZoneId.systemDefault())).stream()
				.filter(issue -> issue.getKey().equals(issueKey))
				.findFirst();
	}

	@Override
	public UserMetrics getMetrics(ProjectSearchParamCommand command, TimeGranularity historyGranularity) {
		List<Issue> allIssues = fetchIssuesForRange(command.projectKeys(), command.startDate(), command.endDate());

		UserMetrics.MetricGenerationQuery metricGenerationQuery = UserMetrics.MetricGenerationQuery.builder()
				.user(getMyself())
				.projectIssues(allIssues)
				.range(DateRange.from(command.startDate(), command.endDate()))
				.granularity(historyGranularity)
				.customMetricsDefinition(this.customMetricsProperties.getCustomMetrics())
				.build();

		return UserMetrics.generate(metricGenerationQuery);
	}

	@Override
	public Page<UserHistoryEvent> getHistory(ProjectSearchParamCommand command, Pageable pageable) {
		if (command.endDate().isBefore(command.startDate())) {
			throw new BusinessException("End date cannot be before start date");
		}

		List<Issue> allIssues = fetchIssuesForRange(command.projectKeys(), command.startDate(), command.endDate());

		String userEmail = getMyself().getEmail();
		DateRange finalRange = DateRange.from(command.startDate(), command.endDate());

		List<UserHistoryEvent> allUserEvents = allIssues.stream()
				.flatMap(issue -> issue.getChanges().stream()
						.filter(change -> issue.isAuthor(userEmail) || (change.isAuthor(userEmail) && change.isReviewDone()))
						.filter(change -> change.getTransitionType() != OTHER && finalRange.contains(change.getDate()))
						.map(change -> userHistoryMapper.toHistoryEvent(issue, change))
				)
				.sorted(Comparator.comparing(UserHistoryEvent::getDate).reversed())
				.toList();

		return PageUtils.toPage(allUserEvents, pageable);
	}

	private List<Issue> fetchIssuesForRange(List<String> projectKeys, LocalDate start, LocalDate end) {
		if (ChronoUnit.DAYS.between(start, end) < 30) {
			return jiraIssueRepository.getIssuesForCustomRange(projectKeys, start, end);
		}
		return Stream.iterate(YearMonth.from(start), month -> !month.isAfter(YearMonth.from(end)), month -> month.plusMonths(1))
				.flatMap(month -> jiraIssueRepository.getIssuesForSpecificMonth(projectKeys, month).stream())
				.toList();
	}
}