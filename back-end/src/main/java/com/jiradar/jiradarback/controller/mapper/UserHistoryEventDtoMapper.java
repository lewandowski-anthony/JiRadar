package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.UserHistoryEventDto;
import com.jiradar.jiradarback.core.model.issuetracker.UserHistoryEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserDtoMapper.class })
public interface UserHistoryEventDtoMapper {

	UserHistoryEventDto toDto(UserHistoryEvent domain);
}
