package com.jiradar.jiradarback.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Collections;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "issue-tracker.metrics")
public class CustomMetricsProperties {

	private Map<String, String> custom;

	public Map<String, String> getCustom() {
		return custom != null ? custom : Collections.emptyMap();
	}
}