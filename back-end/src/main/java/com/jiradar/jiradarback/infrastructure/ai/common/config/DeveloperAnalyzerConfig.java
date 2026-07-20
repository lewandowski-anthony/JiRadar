package com.jiradar.jiradarback.infrastructure.ai.common.config;

import com.jiradar.jiradarback.infrastructure.ai.DeveloperAnalyzer;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("!'${jiradar.ai.provider:none}'.equals('none')")
public class DeveloperAnalyzerConfig {

	@Bean
	public DeveloperAnalyzer developerAnalyzer(ChatModel chatModel) {
		return AiServices.builder(DeveloperAnalyzer.class)
				.chatModel(chatModel)
				.build();
	}
}
