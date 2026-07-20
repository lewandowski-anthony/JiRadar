package com.jiradar.jiradarback.infrastructure.ai.service;

import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;

import java.util.Optional;

public interface DeveloperAnalysisUseCase {

	Optional<DeveloperAnalystResult> analyzeDeveloperProfile(UserMetrics metrics, String langRequested);
}
