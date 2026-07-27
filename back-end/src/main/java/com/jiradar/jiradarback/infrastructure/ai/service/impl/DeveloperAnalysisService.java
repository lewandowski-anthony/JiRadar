package com.jiradar.jiradarback.infrastructure.ai.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.exception.BusinessException;
import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.gateway.AiPromptGateway;
import com.jiradar.jiradarback.infrastructure.ai.service.DeveloperAnalysisUseCase;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.PARAM_AVAILABLE_TITLES;
import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.PARAM_GRANULARITY;
import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.PARAM_METRICS;
import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.PARAM_USER;
import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.SYSTEM_PROMPT;
import static com.jiradar.jiradarback.infrastructure.ai.constant.DeveloperAnalysisConstants.USER_PROMPT_TEMPLATE;
import static com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache.DEVELOPER_ANALYSIS;
import static com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache.DEVELOPER_ANALYSIS_CACHE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeveloperAnalysisService implements DeveloperAnalysisUseCase {

	private final AiPromptGateway aiPromptGateway;

	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	@Override
	@Cacheable(cacheNames =  DEVELOPER_ANALYSIS, key = "#user.email + '-' + #granularity.name()")
	public Optional<DeveloperAnalystResult> getUserAnalyse(
			User user,
			UserMetrics userMetrics,
			TimeGranularity granularity) {

		if (granularity != TimeGranularity.YEAR && granularity != TimeGranularity.MONTH) {
			throw new BusinessException("Cannot analyze user analysis for granularity " + granularity.name());
		}

		log.info("Loading metrics and triggering AI analysis for user: {} with granularity: {}", user.getEmail(), granularity);

		String metricsJson = serializeToJson(userMetrics);

		String titlesWithDescriptions = DeveloperTitle.getFormattedTitlesWithDescriptions();

		DeveloperAnalystResult result = aiPromptGateway.execute(user, chatClient ->
				chatClient.prompt()
						.system(SYSTEM_PROMPT)
						.user(userSpec -> userSpec
								.text(USER_PROMPT_TEMPLATE)
								.param(PARAM_AVAILABLE_TITLES, titlesWithDescriptions)
								.param(PARAM_USER, user.getEmail())
								.param(PARAM_METRICS, metricsJson)
								.param(PARAM_GRANULARITY, granularity.name())
						)
						.call()
						.entity(DeveloperAnalystResult.class)
		);

		return Optional.ofNullable(result);
	}

	private String serializeToJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.warn("Failed to serialize metrics to compact JSON, falling back to toString()", e);
			return String.valueOf(object);
		}
	}
}