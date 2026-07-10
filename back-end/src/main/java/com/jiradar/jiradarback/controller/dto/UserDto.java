package com.jiradar.jiradarback.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "${openapi.dto.user.description}")
public record UserDto(
		@Schema(description = "${openapi.dto.user.email}", example = "john.doe@company.com")
		String email,

		@Schema(description = "${openapi.dto.user.name}", example = "John Doe")
		String name
) {}