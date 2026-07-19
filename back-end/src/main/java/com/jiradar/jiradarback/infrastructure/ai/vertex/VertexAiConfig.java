package com.jiradar.jiradarback.infrastructure.ai.vertex;

import com.jiradar.jiradarback.infrastructure.ai.DeveloperAnalyzer;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "vertex")
public class VertexAiConfig {

    @Value("${jiradar.ai.vertex.project-id}")
    private String projectId;

    @Value("${jiradar.ai.vertex.location}")
    private String location;

    @Value("${jiradar.ai.vertex.model-name:gemini-1.5-pro}")
    private String modelName;

    @Bean
    public ChatModel chatLanguageModel() {
        return VertexAiGeminiChatModel.builder()
                .project(projectId)
                .location(location)
                .modelName(modelName)
                .temperature(0.2f)
                .build();
    }

    @Bean
    public DeveloperAnalyzer developerAnalyzer(ChatModel chatLanguageModel) {
        return AiServices.builder(DeveloperAnalyzer.class)
                .chatModel(chatLanguageModel)
                .build();
    }
}