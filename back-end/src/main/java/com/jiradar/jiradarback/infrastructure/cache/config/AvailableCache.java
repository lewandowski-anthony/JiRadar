package com.jiradar.jiradarback.infrastructure.cache.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public enum AvailableCache {

	JIRA_METRICS_CACHE(AvailableCache.JIRA_METRICS, TimeUnit.MINUTES, 5L),
	JIRA_ISSUE_CACHE(AvailableCache.JIRA_ISSUE, TimeUnit.MINUTES, 5L),
	JIRA_USER_CACHE(AvailableCache.JIRA_USER, TimeUnit.DAYS, 1L);

	private final String cacheName;
	private final TimeUnit cacheRetentionTimeUnit;
	private final Long cacheRetentionTime;

	public static final String JIRA_METRICS = "JIRA_METRICS_CACHE";
	public static final String JIRA_USER = "JIRA_USER_CACHE";
	public static final String JIRA_ISSUE = "JIRA_ISSUE_CACHE";
}
