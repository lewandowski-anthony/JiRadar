package com.jiradar.jiradarback.core.service;

import com.jiradar.jiradarback.common.config.CustomMetricsProperties;
import com.jiradar.jiradarback.core.mapper.UserHistoryMapper;
import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Core domain abstract class defining the contract for interacting with external issue tracking systems.
 * <p>
 * Provides abstract operations for managing user profile context, retrieving specific issues,
 * computing activity metrics, and generating paginated user contribution histories, while enforcing
 * custom SpEL metrics computation engine capability.
 * </p>
 */
public abstract class AbstractIssueTrackerService {

    protected final CustomMetricsProperties customMetricsProperties;
    protected final CustomMetricEngine customMetricEngine;
	protected final UserHistoryMapper userHistoryMapper;

    protected AbstractIssueTrackerService(CustomMetricsProperties customMetricsProperties, UserHistoryMapper userHistoryMapper) {
        this.customMetricsProperties = customMetricsProperties;
		this.userHistoryMapper = userHistoryMapper;
        this.customMetricEngine = new CustomMetricEngine(customMetricsProperties.getCustomMetrics());
    }

    /**
     * Determines whether this service implementation supports the specified tracking provider.
     *
     * @param provider the case-insensitive name of the provider (e.g., "JIRA").
     * @return {@code true} if the provider is supported, {@code false} otherwise.
     */
    public abstract boolean supports(String provider);

    /**
     * Retrieves the profile details of the currently authenticated user.
     *
     * @return the {@link User} entity representing the current user context.
     */
    public abstract User getMyself();

    /**
     * Finds a single issue tracking item using its unique identifier key.
     *
     * @param issueKey the unique business key of the issue (e.g., "ABC-123").
     * @return the corresponding {@link Issue} domain model, or {@code null} if no match is found.
     */
    public abstract Optional<Issue> getIssueByKey(String issueKey);

    /**
     * Calculates and aggregates global activity metrics for the authenticated user within a specified timeframe.
     *
     * @param command            the parameters containing targeted project keys, start date, and end date.
     * @param historyGranularity the temporal grouping level for the metrics timeline (e.g., DAILY, WEEKLY).
     * @return a {@link UserMetrics} object aggregating the computed user productivity data.
     */
    public abstract UserMetrics getMetrics(ProjectSearchParamCommand command, TimeGranularity historyGranularity);

    /**
     * Generates a paginated history timeline of all relevant status changes and review events triggered by the user.
     *
     * @param command  the parameters containing targeted project keys, start date, and end date.
     * @param pageable the page index, size, and sorting parameters.
     * @return a paginated {@link Page} containing the filtered {@link UserHistoryEvent} domain objects.
     */
    public abstract Page<UserHistoryEvent> getHistory(ProjectSearchParamCommand command, Pageable pageable);
}