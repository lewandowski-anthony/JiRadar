package com.jiradar.jiradarback.core.service;

import com.jiradar.jiradarback.core.UserMetricCalculationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomMetricEngine {

	private static final ExpressionParser parser = new SpelExpressionParser();

	private final Map<String, String> customFormulas;

	public Map<String, Object> evaluateCustomMetrics(UserMetricCalculationService serviceContext) {
		if (MapUtils.isEmpty(customFormulas)) {
			return Collections.emptyMap();
		}

		StandardEvaluationContext context = new StandardEvaluationContext(serviceContext);
		Map<String, Object> results = new HashMap<>();

		customFormulas.forEach((metricName, formula) -> {
			try {
				Expression expression = parser.parseExpression(formula);
				Object value = expression.getValue(context);
				results.put(metricName, value);
			} catch (Exception e) {
				results.put(metricName, "ERROR: " + e.getMessage());
			}
		});

		return results;
	}
}