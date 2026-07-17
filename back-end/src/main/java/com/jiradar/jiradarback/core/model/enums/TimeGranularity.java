package com.jiradar.jiradarback.core.model.enums;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.function.UnaryOperator;

import static com.jiradar.jiradarback.core.constant.DateConstant.DAY_LABEL_PATTERN;
import static com.jiradar.jiradarback.core.constant.DateConstant.MONTH_LABEL_PATTERN;
import static com.jiradar.jiradarback.core.constant.DateConstant.WEEK_LABEL_PATTERN;
import static com.jiradar.jiradarback.core.constant.DateConstant.YEAR_LABEL_PATTERN;

public enum TimeGranularity {

	DAY(DAY_LABEL_PATTERN, date -> date.plusDays(1)),
	WEEK(WEEK_LABEL_PATTERN, date -> date.with(TemporalAdjusters.next(DayOfWeek.MONDAY))),
	MONTH(MONTH_LABEL_PATTERN, date -> date.with(TemporalAdjusters.firstDayOfNextMonth())),
	YEAR(YEAR_LABEL_PATTERN, date -> date.with(TemporalAdjusters.firstDayOfNextYear()));

	private final DateTimeFormatter formatter;
	private final UnaryOperator<ZonedDateTime> adjuster;

	TimeGranularity(String pattern, UnaryOperator<ZonedDateTime> adjuster) {
		this.formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
		this.adjuster = adjuster;
	}

	public ZonedDateTime getEndOfPeriod(ZonedDateTime period) {
		return this.adjuster.apply(period).truncatedTo(ChronoUnit.DAYS);
	}

	public String toLabel(ZonedDateTime date) {
		return this == WEEK
			   ? "Week. " + date.format(this.formatter)
			   : date.format(this.formatter);
	}

	public static TimeGranularity fromString(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return TimeGranularity.valueOf(value.toUpperCase().trim());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Time granularity unsupported : " + value);
		}
	}
}