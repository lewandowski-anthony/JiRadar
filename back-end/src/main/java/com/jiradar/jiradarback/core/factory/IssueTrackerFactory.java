package com.jiradar.jiradarback.core.factory;

import com.jiradar.jiradarback.core.service.AbstractIssueTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IssueTrackerFactory {

    private final List<AbstractIssueTrackerService> services;

    public AbstractIssueTrackerService getService(String provider) {
        return services.stream()
                .filter(service -> service.supports(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tracker provider is not supported : " + provider));
    }
}