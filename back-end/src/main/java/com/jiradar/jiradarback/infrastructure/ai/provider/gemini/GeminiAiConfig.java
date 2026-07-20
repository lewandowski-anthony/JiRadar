package com.jiradar.jiradarback.infrastructure.ai.provider.gemini;

import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "gemini")
@RequiredArgsConstructor
public class GeminiAiConfig {

    private final GeminiProperties geminiProperties;

    @Bean
    public ChatClient geminiChatClient() {
        Client genAiClient = Client.builder()
                .apiKey(geminiProperties.getApiKey())
                .build();

        GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
                .model(geminiProperties.getModelName())
                .temperature(0.2)
                .build();

        ChatModel chatModel = GoogleGenAiChatModel.builder()
                .genAiClient(genAiClient)
                .options(options)
                .build();

        return ChatClient.create(chatModel);
    }
}