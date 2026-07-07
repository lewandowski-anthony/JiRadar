package com.jiradar.jiradarback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JiraIssue {

	private String key;
	private String projectKey;
	private String assignee;
	private String summary;
	private JiraStatus jiraStatus;
}
