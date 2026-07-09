package com.jiradar.jiradarback.core.model.issuetracker;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
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

import static com.jiradar.jiradarback.core.model.constant.UserConstant.UNASSIGNED_USER;

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

	public void setAssignee(User assignee) {
		this.assignee = StringUtils.isNotBlank(assignee.getEmail()) ? assignee : UNASSIGNED_USER;
	}

	public boolean isAssignedTo(String email) {
		return this.assignee != null
				&& StringUtils.isNotBlank(this.assignee.getEmail())
				&& this.assignee.getEmail().equalsIgnoreCase(email);
	}

	public boolean isAuthor(String email) {
		return this.assignee != null
				&& StringUtils.isNotBlank(this.assignee.getEmail())
				&& this.assignee.getEmail().equalsIgnoreCase(email);
	}

	public boolean isStartedIn(DateRange range) {
		ZonedDateTime firstStart = this.changes.stream()
				.filter(ChangeLog::isStartedChange)
				.map(ChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		return range.contains(firstStart);
	}

	public boolean isDoneIn(DateRange range) {
		ZonedDateTime lastDone = this.changes.stream()
				.filter(ChangeLog::isDoneChange)
				.map(ChangeLog::getDate)
				.max(ZonedDateTime::compareTo)
				.orElse(null);

		return range.contains(lastDone);
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

		ZonedDateTime absoluteFirst = this.changes.stream()
				.filter(ChangeLog::isReviewRequested)
				.map(ChangeLog::getDate)
				.min(ZonedDateTime::compareTo)
				.orElse(null);

		if (absoluteFirst == null) return 0L;

		return this.changes.stream()
				.filter(ChangeLog::isReviewRequested)
				.filter(change -> change.getDate().isAfter(absoluteFirst))
				.filter(change -> range.contains(change.getDate()))
				.count();
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
