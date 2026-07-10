package com.jiradar.jiradarback.core.model.issuetracker;

import com.jiradar.jiradarback.core.model.enums.TransitionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import static com.jiradar.jiradarback.core.constant.UserConstant.UNASSIGNED_USER;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserHistoryEvent {

	private String issueKey;
	private String issueSummary;
	private String issueType;
	private User issueAssignee;
	private ZonedDateTime date;
	private TransitionType transitionType;

	public User getIssueAssignee() {
		return issueAssignee != null ? issueAssignee : UNASSIGNED_USER;
	}
}