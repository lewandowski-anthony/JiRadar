package com.jiradar.jiradarback.controller.mapper;

import com.jiradar.jiradarback.controller.dto.UserMetricsDto;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserMetricsDtoMapper {

	UserMetricsDto mapToDto(UserMetrics jiraUserMetrics);

	@Mapping(target = "customMetrics", expression = "java(mapCustomMetrics(metric.customMetrics()))")
	UserMetricsDto.MetricDto mapMetric(UserMetrics.Metric metric);

	UserMetricsDto.PeriodicUserMetricsDto mapPeriodicMetric(UserMetrics.PeriodicUserMetrics periodicUserMetrics);

	default List<UserMetricsDto.IssueRateByTypeDto> mapIssueRateByType(Map<String, Double> map) {
		if (map == null) {
			return Collections.emptyList();
		}
		return map.entrySet().stream()
				.map(entry -> new UserMetricsDto.IssueRateByTypeDto(entry.getKey(), entry.getValue()))
				.toList();
	}

	default List<UserMetricsDto.CustomMetricElementDto> mapCustomMetrics(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		return map.entrySet().stream()
				.map(entry -> new UserMetricsDto.CustomMetricElementDto(entry.getKey(), entry.getValue()))
				.toList();
	}

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