package com.jiradar.jiradarback.infrastructure.ai.ollama;

import com.jiradar.jiradarback.infrastructure.ai.DeveloperAnalyzer;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "ollama")
public class OllamaAiConfig {

    @Value("${jiradar.ai.ollama.base-url}")
    private String baseUrl;

    @Value("${jiradar.ai.ollama.model-name}")
    private String modelName;

    @Bean
    public ChatModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
				.logRequests(true)
				.responseFormat(ResponseFormat.builder().type(ResponseFormatType.JSON).build())
				.logResponses(true)
                .temperature(0.2)
                .timeout(Duration.ofMinutes(3))
                .build();
    }

	@Bean
	public DeveloperAnalyzer developerAnalyzer(ChatModel chatModel) {
		return AiServices.builder(DeveloperAnalyzer.class)
				.chatModel(chatModel)
				.build();
	}
}