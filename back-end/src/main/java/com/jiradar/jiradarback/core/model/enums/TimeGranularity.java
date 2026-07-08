package com.jiradar.jiradarback.core.model.enums;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.function.UnaryOperator;

public enum TimeGranularity {

	DAY(ChronoUnit.DAYS, "dd MMM yyyy", date -> date.plusDays(1)),
	WEEK(ChronoUnit.WEEKS, "w - yyyy", date -> date.with(TemporalAdjusters.next(DayOfWeek.MONDAY))),
	MONTH(ChronoUnit.MONTHS, "MMMM yyyy", date -> date.with(TemporalAdjusters.firstDayOfNextMonth())),
	YEAR(ChronoUnit.YEARS, "yyyy", date -> date.with(TemporalAdjusters.firstDayOfNextYear()));

	private final ChronoUnit chronoUnit;
	private final DateTimeFormatter formatter;
	private final UnaryOperator<ZonedDateTime> adjuster;

	TimeGranularity(ChronoUnit chronoUnit, String pattern, UnaryOperator<ZonedDateTime> adjuster) {
		this.chronoUnit = chronoUnit;
		this.formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
		this.adjuster = adjuster;
	}

	public ChronoUnit toChronoUnit() {
		return this.chronoUnit;
	}

	public ZonedDateTime getEndOfPeriod(ZonedDateTime period) {
		return this.adjuster.apply(period).truncatedTo(ChronoUnit.DAYS);
	}

	public String toLabel(ZonedDateTime date) {
		return this == WEEK
			   ? "Sem. " + date.format(this.formatter)
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