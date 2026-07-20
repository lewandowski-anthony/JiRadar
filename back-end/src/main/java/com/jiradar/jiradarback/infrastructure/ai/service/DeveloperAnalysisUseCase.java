package com.jiradar.jiradarback.infrastructure.ai.service;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;

import java.util.Optional;

public interface DeveloperAnalysisUseCase {

	Optional<DeveloperAnalystResult> getUserAnalyse(
			User user,
			UserMetrics userMetrics,
			TimeGranularity granularity);
}
