package com.jiradar.jiradarback.infrastructure.ai.gateway;

import com.jiradar.jiradarback.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AiPromptGateway {

	private final ObjectProvider<@NonNull ChatClient> aiProviderChatClient;

	public <I, R> R execute(I input, Function<ChatClient, R> action) {
		ChatClient chatClient = aiProviderChatClient.getIfAvailable();

		if (chatClient == null) {
			throw new BusinessException("No AI provider was configured on Jiradar.");
		}

		return action.apply(chatClient);
	}
}
