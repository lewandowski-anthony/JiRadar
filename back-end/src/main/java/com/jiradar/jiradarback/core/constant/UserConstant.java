package com.jiradar.jiradarback.core.constant;

import com.jiradar.jiradarback.core.model.issuetracker.User;

public final class UserConstant {

	public static final String UNASSIGNED_NAME = "Unassigned";
	public static final String UNASSIGNED_EMAIL = "unassigned@jira.com";
	public static final User UNASSIGNED_USER = new User(UNASSIGNED_NAME, UNASSIGNED_EMAIL);
}
