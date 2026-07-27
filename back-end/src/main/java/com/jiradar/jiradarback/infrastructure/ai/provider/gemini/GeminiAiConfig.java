package com.jiradar.jiradarback.infrastructure.ai.provider.gemini;

import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "gemini")
@EnableConfigurationProperties(GeminiProperties.class)
@RequiredArgsConstructor
public class GeminiAiConfig {

    private final GeminiProperties gemini;

    @Bean
    public ChatClient geminiChatClient() {
        Client genAiClient = Client.builder()
                .apiKey(gemini.getApiKey())
                .build();

        GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
                .model(gemini.getModelName())
                .responseMimeType("application/json")
                .temperature(0.0)
                .build();

        RetryPolicy noRetryPolicy = RetryPolicy.builder()
                .maxRetries(0)
                .build();

        RetryTemplate noRetryTemplate = new RetryTemplate(noRetryPolicy);

        ChatModel chatModel = GoogleGenAiChatModel.builder()
                .genAiClient(genAiClient)
                .options(options)
                .retryTemplate(noRetryTemplate)
                .build();

        return ChatClient.create(chatModel);
    }
}