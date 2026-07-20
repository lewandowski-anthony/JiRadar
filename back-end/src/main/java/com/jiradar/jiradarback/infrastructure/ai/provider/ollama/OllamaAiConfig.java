package com.jiradar.jiradarback.infrastructure.ai.provider.ollama;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "ollama")
@RequiredArgsConstructor
public class OllamaAiConfig {

	private final OllamaProperties ollamaProperties;

	@Bean
	public ChatClient ollamaChatClient() {
		OllamaApi ollamaApi = OllamaApi.builder()
				.baseUrl(ollamaProperties.getBaseUrl())
				.build();

		ChatModel chatModel = OllamaChatModel.builder()
				.ollamaApi(ollamaApi)
				.options(OllamaChatOptions.builder()
						.model(ollamaProperties.getModelName())
						.temperature(0.2)
						.build())
				.build();

		return ChatClient.create(chatModel);
	}
}