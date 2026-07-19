package com.jiradar.jiradarback.infrastructure.ai.common.model.context;

import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;

import java.util.Arrays;

public record AnalysisContext(
    UserMetrics metrics,
    String language,
	String availableTitles
) {
    public static AnalysisContext create(UserMetrics metrics, String lang) {
        String language = (lang != null && lang.equalsIgnoreCase("fr")) ? "French" : "English";
        String titles = Arrays.toString(DeveloperTitle.values());
        return new AnalysisContext(metrics, language, titles);
    }
}