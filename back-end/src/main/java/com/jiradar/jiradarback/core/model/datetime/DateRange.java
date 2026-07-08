package com.jiradar.jiradarback.core.model.datetime;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public record DateRange(ZonedDateTime from, ZonedDateTime to) {

	public boolean contains(ZonedDateTime date) {
		return date != null && date.isAfter(from) && date.isBefore(to);
	}

	public boolean isMoreThanOneYear() {
		return Duration.between(from, to).toDays() > 366;
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

	public String toLabel(ChronoUnit unit) {
		String pattern = switch (unit) {
			case DAYS -> "dd MMM yyyy";
			case WEEKS -> "w - yyyy";
			case MONTHS -> "MMMM yyyy";
			case YEARS -> "yyyy";
			default -> throw new IllegalArgumentException("Unsupported unit: " + unit);
		};

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.FRANCE);
		return unit == ChronoUnit.WEEKS ? "Sem. " + this.from.format(formatter) : this.from.format(formatter);
	}
}