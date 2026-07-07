package com.jiradar.jiradarback.model.jira;

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

@Getter
@Setter
@AllArgsConstructor
public class JiraIssue {

	private String key;
	private String projectKey;
	private JiraUser assignee;
	private String summary;
	private JiraStatus jiraStatus;
	private List<JiraChangeLog> changes;

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

	public long getReviewReopenedCount(DateRange range) {
		List<JiraChangeLog> reviewRequests = this.changes.stream()
				.filter(change -> range.contains(change.getDate()))
				.filter(JiraChangeLog::isReviewRequested)
				.sorted(Comparator.comparing(JiraChangeLog::getDate))
				.toList();

		return reviewRequests.size() <= 1 ? 0L : reviewRequests.size() - 1;
	}

	public List<Duration> getReviewDurations(DateRange range, java.util.function.Predicate<JiraChangeLog> filterPredicate) {
		List<JiraChangeLog> sortedChanges = this.changes.stream()
				.sorted(Comparator.comparing(JiraChangeLog::getDate))
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

	private ZonedDateTime findLastReviewRequestedDateBefore(List<JiraChangeLog> changes, ZonedDateTime actionDate) {
		return changes.stream()
				.filter(change -> change.getDate().isBefore(actionDate))
				.filter(JiraChangeLog::isReviewRequested)
				.map(JiraChangeLog::getDate)
				.max(ZonedDateTime::compareTo)
				.orElse(null);
	}

	public Duration calculateCycleTimeForIssue() {

		ZonedDateTime startedDate = this.getChanges().stream()
				.filter(JiraChangeLog::isStartedChange)
				.map(JiraChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		ZonedDateTime doneDate = this.getChanges().stream()
				.filter(JiraChangeLog::isDoneChange)
				.map(JiraChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		if (startedDate == null || doneDate == null) {
			return null;
		}

		return Duration.between(startedDate, doneDate);
	}

}
