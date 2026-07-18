package com.jiradar.jiradarback.common.config;

import com.jiradar.jiradarback.core.service.UserMetricCalculationService;
import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.issuetracker.CustomMetricDefinition;
import com.jiradar.jiradarback.core.service.CustomMetricEngine;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.jiradar.jiradarback.core.constant.UserConstant.UNASSIGNED_USER;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "issue-tracker.metrics")
public class CustomMetricsProperties {

	private List<CustomMetricDefinition> customMetrics = new ArrayList<>();

	@PostConstruct
	public void validateSpElFormulas() {
		if (customMetrics.isEmpty()) {
			return;
		}

		DateRange dummyRange = new DateRange(ZonedDateTime.now(ZoneId.systemDefault()).minusDays(1), ZonedDateTime.now(ZoneId.systemDefault()));

		UserMetricCalculationService dummyContext = new UserMetricCalculationService(
				UNASSIGNED_USER,
				Collections.emptyList(),
				dummyRange
		);

		CustomMetricEngine validationEngine = new CustomMetricEngine(customMetrics);
		Map<String, Object> results = validationEngine.evaluateCustomMetrics(dummyContext);

		results.forEach((metricName, value) -> {
			if (value instanceof String string && string.startsWith("ERROR:")) {
				throw new IllegalStateException(
						String.format("Custom metric '%s' is invalid. Detail: %s", metricName, value)
				);
			}
		});
	}
}