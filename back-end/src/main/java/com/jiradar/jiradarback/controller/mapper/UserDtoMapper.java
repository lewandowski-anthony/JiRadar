package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.UserDto;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

	UserDto toDto(User userResponseDto);

}
