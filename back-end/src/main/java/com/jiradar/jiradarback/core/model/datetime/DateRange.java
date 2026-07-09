package com.jiradar.jiradarback.core.model.datetime;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public record DateRange(ZonedDateTime from, ZonedDateTime to) {

	public boolean contains(ZonedDateTime date) {
		return date != null && !date.isBefore(from) && date.isBefore(to);
	}

	public boolean isMoreThanOneYear() {
		return Duration.between(from, to).toDays() > 366;
	}

	public static DateRange from(LocalDate start, LocalDate end) {
		ZoneId zone = ZoneId.systemDefault();
		return new DateRange(start.atStartOfDay(zone), end.plusDays(1).atStartOfDay(zone).minusNanos(1));
	}

	public List<DateRange> splitBy(TimeGranularity unit) {
		List<DateRange> subRanges = new ArrayList<>();
		if (unit == null) return subRanges;

		ZonedDateTime currentStart = this.from;

		while (currentStart.isBefore(this.to)) {
			ZonedDateTime currentEnd = unit.getEndOfPeriod(currentStart);

			if (currentEnd.isAfter(this.to)) {
				currentEnd = this.to;
			}

			subRanges.add(new DateRange(currentStart, currentEnd));
			currentStart = currentEnd;
		}

		return subRanges;
	}
}