package com.jiradar.jiradarback.core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @Value("${jiradar.app.default-timezone:UTC}")
    private String defaultTimezone;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(defaultTimezone));
    }
}