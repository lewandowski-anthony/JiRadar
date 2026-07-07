package com.jiradar.jiradarback.model.jira;

import com.jiradar.jiradarback.model.enums.JiraFieldId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JiraChangeLog {

	private JiraUser author;
	private ZonedDateTime date;
	private String field;
	private JiraFieldId fieldType;
	private String previousValue;
	private String newValue;

	public boolean isStartedChange() {
		return "In Progress".equals(newValue);
	}

	public boolean isDoneChange() {
		return "Done".equals(newValue);
	}

	public boolean isReviewRequested() {
		return JiraFieldId.STATUS == fieldType
				&& "In Review".equalsIgnoreCase(newValue);
	}

	public boolean isReviewActionBy(String devEmail) {
		return StringUtils.isNotBlank(this.author.getEmail())
				&& StringUtils.isNotBlank(devEmail)
				&& this.author.getEmail().equalsIgnoreCase(devEmail)
				&& JiraFieldId.STATUS == this.fieldType
				&& "In Review".equalsIgnoreCase(this.previousValue)
				&& !"In Review".equalsIgnoreCase(this.newValue);
	}

	private boolean hasHappenedBetweenPeriod(ZonedDateTime start, ZonedDateTime end) {
		return date.isBefore(end) && date.isAfter(start);
	}
}
