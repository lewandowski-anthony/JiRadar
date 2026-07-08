package com.jiradar.jiradarback.core;

import com.jiradar.jiradarback.core.model.command.MetricsQueryCommand;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IssueTrackerService {

	boolean supports(String provider);

	User getMyself();

	Issue getIssueByKey(String issueKey);

	UserMetrics getMetrics(MetricsQueryCommand command);
}
