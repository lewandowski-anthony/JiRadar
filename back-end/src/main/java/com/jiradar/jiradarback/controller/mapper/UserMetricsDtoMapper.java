package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface UserMetricsDtoMapper {

	UserMetricsDto mapToDto(UserMetrics jiraUserMetrics);

	UserMetricsDto.MetricDto mapMetric(UserMetrics.Metric metric);

	UserMetricsDto.PeriodicUserMetricsDto mapPeriodicMetric(UserMetrics.PeriodicUserMetrics periodicUserMetrics);

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