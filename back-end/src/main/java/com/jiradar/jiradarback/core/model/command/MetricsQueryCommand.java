package com.jiradar.jiradarback.core.model.command;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record MetricsQueryCommand(
		List<String> projectKeys,
		LocalDate startDate,
		LocalDate endDate,
		TimeGranularity historyGranularity
) {

}
