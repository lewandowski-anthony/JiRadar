package com.jiradar.jiradarback.infrastructure.jira.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties("jira.config")
public class JiraProperties {

    private String user;
    private String url;
    private String token;
    private StatusesMapping statuses;

    @Getter
    @Setter
    public static class StatusesMapping {
       private List<String> startDevelopment;
       private List<String> requestReview;
       private List<String> done;
    }
}