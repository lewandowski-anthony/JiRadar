package com.jiradar.jiradarback.core;

import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;

import java.util.List;

public interface IssueTrackerService {

	boolean supports(String provider);

	User getMyself();

	Issue getIssueByKey(String issueKey);

	UserMetrics getMetrics(List<String> projects);
}
