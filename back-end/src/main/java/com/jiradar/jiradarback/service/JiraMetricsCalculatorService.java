package com.jiradar.jiradarback.service;

import com.jiradar.jiradarback.model.datetime.DateRange;
import com.jiradar.jiradarback.model.jira.JiraChangeLog;
import com.jiradar.jiradarback.model.jira.JiraIssue;
import com.jiradar.jiradarback.model.jira.JiraUserMetrics;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record JiraMetricsCalculatorService(List<JiraIssue> jiraIssues, DateRange range) {

	public JiraUserMetrics calculateForPeriod(String devEmail) {
		List<Duration> reviewDurations = getAllReviewDurations(devEmail);

		JiraUserMetrics jiraUserMetric = new JiraUserMetrics();
		jiraUserMetric.setFrom(range.from());
		jiraUserMetric.setTo(range.to());
		jiraUserMetric.setNumberOfIssueStarted(countIssuesStartedBetween());
		jiraUserMetric.setNumberOfIssueDone(countIssuesDoneBetween());
		jiraUserMetric.setAverageCycleTime(getIssuesAverageCycleTime());
		jiraUserMetric.setAverageReviewTime(getAverageReviewTime(reviewDurations));
		jiraUserMetric.setNumberOfReviewDone((long) reviewDurations.size());
		jiraUserMetric.setNumberOfReviewReopened(countReviewReopenedBetween());
		return jiraUserMetric;
	}

	public Duration getIssuesAverageCycleTime() {
		List<Duration> validDurations = jiraIssues.stream()
				.map(JiraIssue::calculateCycleTimeForIssue)
				.filter(Objects::nonNull)
				.toList();

		if (validDurations.isEmpty()) {
			return Duration.ZERO;
		}

		return validDurations.stream()
				.reduce(Duration.ZERO, Duration::plus)
				.dividedBy(validDurations.size());
	}

	private Long countIssuesStartedBetween() {
		return jiraIssues.stream()
				.filter(issue -> issue.getChanges().stream()
						.anyMatch(change -> range.contains(change.getDate()) && change.isStartedChange()))
				.count();
	}

	private Long countIssuesDoneBetween() {
		return jiraIssues.stream()
				.filter(issue -> issue.getChanges().stream()
						.anyMatch(change -> range.contains(change.getDate()) && change.isDoneChange()))
				.count();
	}

	private Long countReviewReopenedBetween() {
		return jiraIssues.stream()
				.mapToLong(this::countReviewReopenedForIssue)
				.sum();
	}

	private long countReviewReopenedForIssue(JiraIssue issue) {
		List<JiraChangeLog> reviewRequests = issue.getChanges().stream()
				.filter(change -> range.contains(change.getDate()))
				.filter(JiraChangeLog::isReviewRequested)
				.sorted(Comparator.comparing(JiraChangeLog::getDate))
				.toList();

		return reviewRequests.size() <= 1 ? 0L : reviewRequests.size() - 1;
	}

	private List<Duration> getAllReviewDurations(String devEmail) {
		return jiraIssues.stream()
				.flatMap(issue -> calculateTimeToReviewOtherJiras(issue, devEmail).stream())
				.filter(Objects::nonNull)
				.toList();
	}

	private Duration getAverageReviewTime(List<Duration> reviewDurations) {
		if (reviewDurations.isEmpty()) {
			return Duration.ZERO;
		}

		Duration totalDuration = reviewDurations.stream().reduce(Duration.ZERO, Duration::plus);
		return totalDuration.dividedBy(reviewDurations.size());
	}

	private List<Duration> calculateTimeToReviewOtherJiras(JiraIssue issue, String devEmail) {
		if (issue.getAssignee() != null && issue.getAssignee().getEmail().equalsIgnoreCase(devEmail)) {
			return List.of();
		}

		List<JiraChangeLog> sortedChanges = issue
				.getChanges().stream()
				.sorted(Comparator.comparing(JiraChangeLog::getDate))
				.toList();

		return sortedChanges.stream()
				.filter(change -> change.isReviewActionBy(devEmail) && range.contains(change.getDate()))
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
}