package com.jiradar.jiradarback.core;

import com.jiradar.jiradarback.core.model.command.ProjectSearchParamCommand;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IssueTrackerService {

	boolean supports(String provider);

	User getMyself();

	Issue getIssueByKey(String issueKey);

	UserMetrics getMetrics(ProjectSearchParamCommand command, TimeGranularity historyGranularity);

	Page<UserHistoryEvent> getHistory(ProjectSearchParamCommand command, Pageable pageable);
}
