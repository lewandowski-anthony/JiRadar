package com.jiradar.jiradarback.core.model.issuetracker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMetricDefinition {
	private String name;
	private String formula;
}