package com.jiradar.jiradarback.infrastructure.issueTracker.jira.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties("issue-tracker.jira.config")
public class JiraProperties {

    private String url;
    private StatusesMapping statuses;

    @Getter
    @Setter
    public static class StatusesMapping {
       private List<String> startDevelopment;
       private List<String> requestReview;
       private List<String> done;
    }
}