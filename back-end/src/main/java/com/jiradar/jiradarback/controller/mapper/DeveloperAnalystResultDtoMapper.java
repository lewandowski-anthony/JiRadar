package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.DeveloperAnalystResultDto;
import com.jiradar.jiradarback.infrastructure.ai.common.model.DeveloperAnalystResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeveloperAnalystResultDtoMapper {

	@Mapping(
			target = "assignedTitle",
			expression = "java(domain.assignedTitle() != null ? domain.assignedTitle().name() : null)"
	)
	DeveloperAnalystResultDto toDto(DeveloperAnalystResult domain);

	DeveloperAnalystResultDto.KeyMetricInterpretationsDto toKeyMetricInterpretationsDto(
			DeveloperAnalystResult.KeyMetricInterpretations domainKeyMetric
	);
}
