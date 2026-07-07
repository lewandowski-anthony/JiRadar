package com.jiradar.jiradarback.model.jira;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JiraIssue {

	private String key;
	private String projectKey;
	private JiraUser assignee;
	private String summary;
	private JiraStatus jiraStatus;
	private List<JiraChangeLog> changes;

	public Duration calculateCycleTimeForIssue() {

		ZonedDateTime startedDate = this.getChanges().stream()
				.filter(JiraChangeLog::isStartedChange)
				.map(JiraChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		ZonedDateTime doneDate = this.getChanges().stream()
				.filter(JiraChangeLog::isDoneChange)
				.map(JiraChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		if (startedDate == null || doneDate == null) {
			return null;
		}

		return Duration.between(startedDate, doneDate);
	}

}
