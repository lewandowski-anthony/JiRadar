package com.jiradar.jiradarback.core.model.command;

import java.time.LocalDate;
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
}
