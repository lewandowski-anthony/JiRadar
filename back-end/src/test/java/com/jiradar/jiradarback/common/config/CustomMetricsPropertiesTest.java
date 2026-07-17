package com.jiradar.jiradarback.common.config;

import com.jiradar.jiradarback.core.model.datetime.DateRange;
import com.jiradar.jiradarback.core.model.issuetracker.CustomMetricDefinition;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.service.CustomMetricEngine;
import com.jiradar.jiradarback.core.service.UserMetricCalculationService;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomMetricsPropertiesTest {

	@Test
	void should_return_empty_map_when_formulas_list_is_empty() {
		CustomMetricEngine engine = new CustomMetricEngine(Collections.emptyList());
		Map<String, Object> results = engine.evaluateCustomMetrics(null);
		assertTrue(results.isEmpty());
	}

	@Test
	void should_catch_runtime_evaluation_error_without_crashing() {
		CustomMetricDefinition def = new CustomMetricDefinition();
		def.setName("divide-by-zero");
		def.setFormula("numberOfIssueDone / 0");

		CustomMetricEngine engine = new CustomMetricEngine(List.of(def));

		UserMetricCalculationService context = new UserMetricCalculationService(
				User.builder().build(),
				Collections.emptyList(),
				new DateRange(ZonedDateTime.now(), ZonedDateTime.now())
		);

		Map<String, Object> results = engine.evaluateCustomMetrics(context);

		assertTrue(results.containsKey("divide-by-zero"));
		assertTrue(results.get("divide-by-zero").toString().startsWith("ERROR:"));
	}
}