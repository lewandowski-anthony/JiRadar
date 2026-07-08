package com.jiradar.jiradarback.service;

import com.jiradar.jiradarback.model.issuetracker.Issue;
import com.jiradar.jiradarback.model.issuetracker.UserMetrics;

import java.util.List;

public interface IssueTrackerService {

	Issue getIssueByKey(String issueKey);

	UserMetrics getMetrics(List<String> projects);
}
