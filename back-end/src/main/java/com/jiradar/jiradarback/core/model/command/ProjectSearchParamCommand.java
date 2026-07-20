package com.jiradar.jiradarback.core.model.command;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public record ProjectSearchParamCommand(
		List<String> projectKeys,
		LocalDate startDate,
		LocalDate endDate
) {

	public ProjectSearchParamCommand {
		if (projectKeys == null || projectKeys.isEmpty()) {
			throw new IllegalArgumentException("projects is empty");
		}
		if (startDate == null) {
			throw new IllegalArgumentException("startDate is null");
		}
		if (endDate == null) {
			throw new IllegalArgumentException("endDate is null");
		}
	}

	public static ProjectSearchParamCommand fromGranularity(List<String> projectKeys, TimeGranularity granularity, long amount) {
		LocalDate endDate = LocalDate.now(ZoneId.systemDefault());
		LocalDate startDate = granularity.getFromNow(amount).toLocalDate();
		return new ProjectSearchParamCommand(projectKeys, startDate, endDate);
	}
}
