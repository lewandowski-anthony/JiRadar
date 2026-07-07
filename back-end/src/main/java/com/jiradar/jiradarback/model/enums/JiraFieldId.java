package com.jiradar.jiradarback.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum JiraFieldId {

	STATUS_FIELD("status"),
	FIX_VERSION_FIELD("fixVersions"),
	DESCRIPTION_FIELD("description"),
	STATUS("status"),
	ASSIGNEE("assignee"),
	CREATED("created"),
	UPDATED("updated"),
	SUMMARY("summary"),
	PROJECT("project"),
	ISSUETYPE("issueType"),
	COMMENT("comment"),
	DESCRIPTION("description"),
	PRIORITY("priority"),
	RESOLUTION("resolution"),
	CHANGELOG("changelog"),
	UNKNOWN("unknown");

	private String fieldName;

	public static List<JiraFieldId> getMandatoryFields() {
		return Arrays.asList(
				JiraFieldId.SUMMARY,
				JiraFieldId.STATUS,
				JiraFieldId.ASSIGNEE,
				JiraFieldId.CREATED,
				JiraFieldId.UPDATED,
				JiraFieldId.ISSUETYPE,
				JiraFieldId.PRIORITY
		);
	}

	public static List<String> getMandatoryFieldNames() {
		return getMandatoryFields().stream().map(JiraFieldId::getFieldName).toList();
	}
}
