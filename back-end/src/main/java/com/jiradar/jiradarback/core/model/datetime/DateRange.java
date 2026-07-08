package com.jiradar.jiradarback.core.model.datetime;

import lombok.Builder;

import java.time.Duration;
import java.time.ZonedDateTime;

public record DateRange(ZonedDateTime from, ZonedDateTime to) {

	public boolean contains(ZonedDateTime date) {
		if (date == null) {
			return false;
		}
		return date.isAfter(from) && date.isBefore(to);
	}

	public boolean isMoreThanOneYear() {
		return Duration.between(from, to).toDays() > 366;
	}
}
