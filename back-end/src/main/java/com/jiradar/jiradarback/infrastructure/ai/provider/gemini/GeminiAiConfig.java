package com.jiradar.jiradarback.infrastructure.ai.provider.gemini;

import com.jiradar.jiradarback.infrastructure.ai.DeveloperAnalyzer;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "gemini")
public class GeminiAiConfig {

    @Value("${jiradar.ai.gemini.api-key}")
    private String apiKey;

    @Value("${jiradar.ai.gemini.model-name:gemini-1.5-pro}")
    private String modelName;

    @Bean
    public ChatModel chatModel() {
        return GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
				.logRequestsAndResponses(true)
                .build();
    }
}