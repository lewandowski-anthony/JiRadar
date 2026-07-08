package com.jiradar.jiradarback.model.issuetracker;

import com.jiradar.jiradarback.model.datetime.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@Setter
@AllArgsConstructor
public class Issue {

	private String key;
	private String projectKey;
	private User assignee;
	private String summary;
	private IssueType type;
	private List<ChangeLog> changes;

	public boolean isAssignedTo(String email) {
		return this.assignee != null
				&& StringUtils.isNotBlank(this.assignee.getEmail())
				&& this.assignee.getEmail().equalsIgnoreCase(email);
	}

	public boolean isStartedIn(DateRange range) {
		return this.changes.stream()
				.anyMatch(change -> range.contains(change.getDate()) && change.isStartedChange());
	}

	public boolean isDoneIn(DateRange range) {
		return this.changes.stream()
				.anyMatch(change -> range.contains(change.getDate()) && change.isDoneChange());
	}

	public boolean isActiveOn(ZonedDateTime date) {
		return this.changes.stream()
				.filter(change -> change.getDate().isBefore(date))
				.filter(change -> change.isStartedChange() || change.isDoneChange())
				.max(Comparator.comparing(ChangeLog::getDate))
				.map(ChangeLog::isStartedChange)
				.orElse(false);
	}

	public long getReviewReopenedCount(DateRange range) {
		List<ChangeLog> reviewRequests = this.changes.stream()
				.filter(change -> range.contains(change.getDate()))
				.filter(ChangeLog::isReviewRequested)
				.sorted(Comparator.comparing(ChangeLog::getDate))
				.toList();

		return reviewRequests.size() <= 1 ? 0L : reviewRequests.size() - 1;
	}

	public List<Duration> getReviewDurations(DateRange range, Predicate<ChangeLog> filterPredicate) {
		List<ChangeLog> sortedChanges = this.changes.stream()
				.sorted(Comparator.comparing(ChangeLog::getDate))
				.toList();

		return sortedChanges.stream()
				.filter(change -> range.contains(change.getDate()))
				.filter(filterPredicate)
				.map(actionChange -> {
					ZonedDateTime reviewStart = findLastReviewRequestedDateBefore(sortedChanges, actionChange.getDate());
					return reviewStart != null ? Duration.between(reviewStart, actionChange.getDate()) : null;
				})
				.filter(Objects::nonNull)
				.toList();
	}

	private ZonedDateTime findLastReviewRequestedDateBefore(List<ChangeLog> changes, ZonedDateTime actionDate) {
		return changes.stream()
				.filter(change -> change.getDate().isBefore(actionDate))
				.filter(ChangeLog::isReviewRequested)
				.map(ChangeLog::getDate)
				.max(ZonedDateTime::compareTo)
				.orElse(null);
	}

	public Duration calculateCycleTimeForIssue() {

		ZonedDateTime startedDate = this.getChanges().stream()
				.filter(ChangeLog::isStartedChange)
				.map(ChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		ZonedDateTime doneDate = this.getChanges().stream()
				.filter(ChangeLog::isDoneChange)
				.map(ChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		if (startedDate == null || doneDate == null) {
			return null;
		}

		return Duration.between(startedDate, doneDate);
	}

}
