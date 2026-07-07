package com.jiradar.jiradarback.model.jira;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JiraStatus {

	private String id;
	private String name;
	private String iconUrl;
}
