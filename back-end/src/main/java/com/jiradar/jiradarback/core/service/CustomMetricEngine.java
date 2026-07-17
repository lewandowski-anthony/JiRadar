package com.jiradar.jiradarback.core.service;

import com.jiradar.jiradarback.core.model.issuetracker.CustomMetricDefinition;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.DataBindingMethodResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMetricEngine {

	private static final ExpressionParser parser = new SpelExpressionParser();
	private final Map<String, Expression> compiledFormulas;

	public CustomMetricEngine(List<CustomMetricDefinition> customFormulas) {
		if (CollectionUtils.isEmpty(customFormulas)) {
			this.compiledFormulas = Collections.emptyMap();
		} else {
			this.compiledFormulas = new HashMap<>();
			customFormulas.forEach(customMetricDefinition ->
					this.compiledFormulas.put(customMetricDefinition.getName(), parser.parseExpression(customMetricDefinition.getFormula()))
			);
		}
	}

	public Map<String, Object> evaluateCustomMetrics(UserMetricCalculationService serviceContext) {
		if (compiledFormulas.isEmpty()) {
			return Collections.emptyMap();
		}

		EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding()
				.withMethodResolvers(DataBindingMethodResolver.forInstanceMethodInvocation())
				.withRootObject(serviceContext)
				.build();

		Map<String, Object> results = new HashMap<>();

		compiledFormulas.forEach((metricName, expression) -> {
			try {
				results.put(metricName, expression.getValue(context));
			} catch (Exception e) {
				results.put(metricName, "ERROR: " + e.getMessage());
			}
		});

		return results;
	}
}