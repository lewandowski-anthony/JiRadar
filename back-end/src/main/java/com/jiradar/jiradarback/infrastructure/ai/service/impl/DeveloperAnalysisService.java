package com.jiradar.jiradarback.infrastructure.ai.service.impl;

import com.jiradar.jiradarback.core.model.enums.TimeGranularity;
import com.jiradar.jiradarback.core.model.issuetracker.User;
import com.jiradar.jiradarback.core.model.issuetracker.UserMetrics;
import com.jiradar.jiradarback.exception.BusinessException;
import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.gateway.AiPromptGateway;
import com.jiradar.jiradarback.infrastructure.ai.service.DeveloperAnalysisUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeveloperAnalysisService implements DeveloperAnalysisUseCase {

	private final AiPromptGateway aiPromptGateway;

	@Override
	@Cacheable(value = "developerAnalysis", key = "#user.email + '-' + #granularity.name()")
	public Optional<DeveloperAnalystResult> getUserAnalyse(
			User user,
			UserMetrics userMetrics,
			TimeGranularity granularity) {

		if (granularity != TimeGranularity.YEAR && granularity != TimeGranularity.MONTH) {
			throw new BusinessException("Cannot analyze user analysis for granularity " + granularity.name());
		}

		log.info("Loading metrics and triggering AI analysis for user: {} with granularity: {}", user.getEmail(), granularity);

		List<String> allowedTitles = Arrays.stream(DeveloperTitle.values())
				.map(Enum::name)
				.toList();

		DeveloperAnalystResult result = aiPromptGateway.execute(user, chatClient ->
				chatClient.prompt()
						.system("""
                   Tu es un Tech Lead expert en analyse de productivité logicielle. 
                   Analyse les métriques du développeur ci-dessous et retourne le résultat.
                   Tu dois obligatoirement choisir un titre parmi la liste suivante : {availableTitles}.
                   Réponds en anglais.
                   """)
						.user(userSpec -> userSpec
								.text("""
                       Développeur : {user}
                       Métriques de performance : {metrics}
                       Granularité temporelle : {granularity}
                       """)
								.param("availableTitles", allowedTitles)
								.param("user", user.getEmail())
								.param("metrics", userMetrics)
								.param("granularity", granularity.name())
						)
						.call()
						.entity(DeveloperAnalystResult.class)
		);

		return Optional.ofNullable(result);
	}
}