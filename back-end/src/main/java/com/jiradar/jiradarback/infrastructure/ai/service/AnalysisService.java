package com.jiradar.jiradarback.infrastructure.ai.service;

import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.context.AnalysisContext;
import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.DeveloperAnalyzer;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final ObjectProvider<@NonNull DeveloperAnalyzer> developerAnalyzerProvider;

    public Optional<DeveloperAnalystResult> analyzeDeveloperProfile(UserMetrics metrics, String langRequested) {
        DeveloperAnalyzer analyzer = developerAnalyzerProvider.getIfAvailable();

        if (analyzer == null) {
            return Optional.empty();
        }

		List<String> allowedTitles = Arrays.stream(DeveloperTitle.values())
				.map(Enum::name)
				.toList();

		Map<String, Object> context = new HashMap<>();
		context.put("metrics", metrics);
		context.put("language", langRequested);
		context.put("availableTitles", allowedTitles);

		return Optional.of(analyzer.analyze(context));
    }
}