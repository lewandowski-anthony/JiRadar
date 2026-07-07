package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.model.jira.JiraUserMetrics;
import org.mapstruct.Mapper;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface UserMetricsDtoMapper {

	UserMetricsDto mapToDto(JiraUserMetrics jiraUserMetrics);

	default String formatDuration(Duration duration) {
		if (duration == null || duration.isZero()) {
			return "0m";
		}

		long days = duration.toDays();
		long hours = duration.toHoursPart();
		long minutes = duration.toMinutesPart();

		if (days > 0) {
			return String.format("%dd %dh %dm", days, hours, minutes);
		} else if (hours > 0) {
			return String.format("%dh %dm", hours, minutes);
		} else {
			return String.format("%dm", minutes);
		}
	}
}
