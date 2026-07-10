package com.jiradar.jiradarback.core.mapper;

import com.jiradar.jiradarback.core.model.issuetracker.ChangeLog;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {

	@Mapping(target = "issueKey", source = "issue.key")
	@Mapping(target = "issueSummary", source = "issue.summary")
	@Mapping(target = "issueType", source = "issue.type.name")
	@Mapping(target = "issueAssignee", source = "issue.assignee")
	@Mapping(target = "date", source = "change.date")
	@Mapping(target = "transitionType", source = "change.transitionType")
	UserHistoryEvent toHistoryEvent(Issue issue, ChangeLog change);
}