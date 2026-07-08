package com.jiradar.jiradarback.core;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.model.issuetracker.IssueType;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMetricCalculationService {

    private final DateRange range;
    private final List<Issue> userIssues;
    private final List<Duration> devReviewDurations;
    private final List<Duration> teamReviewDurations;

    private final long startedCount;
    private final long doneCount;
    private final long reopenedCount;

    public UserMetricCalculationService(User user, List<Issue> projectIssues, DateRange range) {
        this.range = range;

		List<Issue> projectIssuesInDateRange = projectIssues.stream()
                .filter(issue -> issue.isStartedIn(range))
                .toList();

        this.userIssues = projectIssuesInDateRange.stream()
                .filter(issue -> issue.isAssignedTo(user.getEmail()))
                .toList();

        this.devReviewDurations = projectIssuesInDateRange.stream()
                .filter(issue -> !issue.isAssignedTo(user.getEmail()))
                .flatMap(issue -> issue.getReviewDurations(range, change -> change.isReviewActionBy(user.getEmail())).stream())
                .toList();

        this.teamReviewDurations = projectIssuesInDateRange.stream()
                .filter(issue -> !issue.isAssignedTo(user.getEmail()))
                .flatMap(issue -> issue.getReviewDurations(range, change -> change.getAuthor() != null
                        && StringUtils.isNotBlank(change.getAuthor().getEmail())
                        && change.isReviewActionBy(change.getAuthor().getEmail())).stream())
                .toList();

        this.startedCount = userIssues.stream().filter(issue -> issue.isStartedIn(range)).count();
        this.doneCount = userIssues.stream().filter(issue -> issue.isDoneIn(range)).count();
        this.reopenedCount = userIssues.stream().mapToLong(issue -> issue.getReviewReopenedCount(range)).sum();
    }

    public long getNumberOfIssueStarted() {
        return startedCount;
    }

    public long getNumberOfIssueDone() {
        return doneCount;
    }

    public long getNumberOfReviewDone() {
        return devReviewDurations.size();
    }

    public long getNumberOfReviewReopened() {
        return reopenedCount;
    }

    public Duration calculateAverageCycleTime() {
        List<Duration> cycles = userIssues.stream()
                .filter(issue -> issue.isDoneIn(range))
                .map(Issue::calculateCycleTimeForIssue)
                .filter(Objects::nonNull)
                .toList();
        return calculateAverage(cycles);
    }

    public Duration calculateAverageReviewTime() {
        return calculateAverage(devReviewDurations);
    }

    public double calculateTeamReviewParticipationRate() {
        return calculatePercentage(devReviewDurations.size(), teamReviewDurations.size());
    }

    public double calculateDeliverySuccessRate() {
        return calculatePercentage(doneCount, startedCount);
    }

    public double calculatePingPongReviewRate() {
        if (doneCount == 0) return 0.0;
        return calculatePercentage(reopenedCount, doneCount);
    }

    public double calculateParallelJiraInProgressRate() {
        ZonedDateTime startDay = range.from().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime endDay = range.to().truncatedTo(ChronoUnit.DAYS);

        long totalDays = ChronoUnit.DAYS.between(startDay, endDay) + 1;
        if (totalDays <= 0) return 0.0;

        long totalActiveTicketsCombined = Stream.iterate(startDay, day -> day.plusDays(1))
                .limit(totalDays)
                .mapToLong(day -> userIssues.stream().filter(issue -> issue.isActiveOn(day)).count())
                .sum();

        double rawWip = (double) totalActiveTicketsCombined / totalDays;
        return Math.round(rawWip * 100.0) / 100.0;
    }

    public Map<String, Double> getDoneIssuesTypeDistribution() {
        List<Issue> doneIssues = userIssues.stream()
                .filter(issue -> issue.isDoneIn(range))
                .toList();

        if (doneIssues.isEmpty()) {
            return Collections.emptyMap();
        }

        long totalDone = doneIssues.size();
        Map<String, Long> countByType = doneIssues.stream()
				.map(Issue::getType)
                .collect(Collectors.groupingBy(IssueType::getName, Collectors.counting()));

        Map<String, Double> distribution = new HashMap<>();
        countByType.forEach((type, count) ->
                distribution.put(type, calculatePercentage(count, totalDone))
        );
        return distribution;
    }

    private Duration calculateAverage(List<Duration> durations) {
        if (durations.isEmpty()) return Duration.ZERO;
        return durations.stream().reduce(Duration.ZERO, Duration::plus).dividedBy(durations.size());
    }

    private double calculatePercentage(long part, long total) {
        if (total == 0) return 100.0;
        double rate = ((double) part / total) * 100.0;
        return Math.round(rate * 100.0) / 100.0;
    }
}