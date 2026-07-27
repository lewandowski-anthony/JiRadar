package com.jiradar.jiradarback.config;

import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.common.model.response.DeveloperAnalystResult;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.function.Consumer;

@TestConfiguration
public class AiTestConfig {

	@Bean
	@Primary
	public ChatClient mockChatClient() {
		ChatClient mockClient = Mockito.mock(ChatClient.class);
		ChatClient.ChatClientRequestSpec requestSpec = Mockito.mock(ChatClient.ChatClientRequestSpec.class);
		ChatClient.CallResponseSpec callSpec = Mockito.mock(ChatClient.CallResponseSpec.class);

		DeveloperAnalystResult dummyResult = new DeveloperAnalystResult(
				"Solid developer performance.",
				List.of("Detail oriented", "Quick bug resolution"),
				List.of("Increase code review participation"),
				DeveloperTitle.BUG_FIXER,
				85, 70, 90, 80,
				"Low code review count",
				"Focus on reviewing PRs",
				List.of("Review at least 2 PRs per week"),
				"Senior Engineer",
				"Pair programming on complex features",
				new DeveloperAnalystResult.KeyMetricInterpretations("22 tasks", "4 days average", "0 PRs reviewed"),
				List.of("Risk of isolated development")
		);

		when(mockClient.prompt()).thenReturn(requestSpec);
		when(requestSpec.system(any(String.class))).thenReturn(requestSpec);
		when(requestSpec.user(any(Consumer.class))).thenReturn(requestSpec);
		when(requestSpec.call()).thenReturn(callSpec);
		when(callSpec.entity(DeveloperAnalystResult.class)).thenReturn(dummyResult);

		return mockClient;
	}
}