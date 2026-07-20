package com.jiradar.jiradarback.infrastructure.ai.provider.vertex;

import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jiradar.ai.provider", havingValue = "vertex")
@RequiredArgsConstructor
public class VertexAiConfig {

	private final VertexProperties vertexProperties;

	@Bean
	public ChatClient vertexAiChatClient() {
		Client genAiClient = Client.builder()
				.project(vertexProperties.getProjectId())
				.location(vertexProperties.getLocation())
				.vertexAI(true)
				.build();

		GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
				.model(vertexProperties.getModelName())
				.temperature(0.2)
				.build();

		ChatModel chatModel = GoogleGenAiChatModel.builder()
				.genAiClient(genAiClient)
				.options(options)
				.build();

		return ChatClient.create(chatModel);
	}
}