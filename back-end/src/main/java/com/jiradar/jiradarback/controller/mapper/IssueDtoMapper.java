package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.IssueDto;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IssueDtoMapper {

	@Mapping(target = "type", source = "type.name")
	IssueDto toDto(Issue issue);

}
