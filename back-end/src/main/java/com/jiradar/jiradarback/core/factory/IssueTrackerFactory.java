package com.jiradar.jiradarback.core.factory;

import com.jiradar.jiradarback.core.IssueTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IssueTrackerFactory {

    private final List<IssueTrackerService> services;

    public IssueTrackerService getService(String provider) {
        return services.stream()
                .filter(service -> service.supports(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tracker provider is not supported : " + provider));
    }
}