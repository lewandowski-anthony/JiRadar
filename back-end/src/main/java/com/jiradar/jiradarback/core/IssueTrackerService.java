package com.jiradar.jiradarback.core;

import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Core domain interface defining the contract for interacting with external issue tracking systems.
 * <p>
 * Provides abstract operations for managing user profile context, retrieving specific issues,
 * computing activity metrics, and generating paginated user contribution histories.
 * </p>
 */
public interface IssueTrackerService {

	/**
	 * Determines whether this service implementation supports the specified tracking provider.
	 *
	 * @param provider the case-insensitive name of the provider (e.g., "JIRA").
	 * @return {@code true} if the provider is supported, {@code false} otherwise.
	 */
	boolean supports(String provider);

	/**
	 * Retrieves the profile details of the currently authenticated user.
	 *
	 * @return the {@link User} entity representing the current user context.
	 */
	User getMyself();

	/**
	 * Finds a single issue tracking item using its unique identifier key.
	 *
	 * @param issueKey the unique business key of the issue (e.g., "ABC-123").
	 * @return the corresponding {@link Issue} domain model, or {@code null} if no match is found.
	 */
	Issue getIssueByKey(String issueKey);

	/**
	 * Calculates and aggregates global activity metrics for the authenticated user within a specified timeframe.
	 *
	 * @param command            the parameters containing targeted project keys, start date, and end date.
	 * @param historyGranularity the temporal grouping level for the metrics timeline (e.g., DAILY, WEEKLY).
	 * @return a {@link UserMetrics} object aggregating the computed user productivity data.
	 */
	UserMetrics getMetrics(ProjectSearchParamCommand command, TimeGranularity historyGranularity);

	/**
	 * Generates a paginated history timeline of all relevant status changes and review events triggered by the user.
	 *
	 * @param command  the parameters containing targeted project keys, start date, and end date.
	 * @param pageable the page index, size, and sorting parameters.
	 * @return a paginated {@link Page} containing the filtered {@link UserHistoryEvent} domain objects.
	 */
	Page<UserHistoryEvent> getHistory(ProjectSearchParamCommand command, Pageable pageable);
}