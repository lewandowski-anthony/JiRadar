package com.jiradar.jiradarback.stepdefinitions.ai;

import com.jiradar.jiradarback.infrastructure.ai.common.model.DeveloperAnalystResult;
import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.gateway.AiPromptGateway;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AiGatewayStepDefinitions {

	@MockitoBean
	private AiPromptGateway aiPromptGateway;

	private DeveloperAnalystResult configuredResult;

	@Before
	public void setupAiPromptGatewayMock() {
		configuredResult = createDefaultResult("Solid developer performance.", DeveloperTitle.BUG_FIXER);

		when(aiPromptGateway.execute(any())).thenAnswer(invocation -> {
			Function<ChatClient, DeveloperAnalystResult> executionLambda = invocation.getArgument(0);

			ChatClient chatClientMock = mock(ChatClient.class);
			ChatClient.ChatClientRequestSpec requestSpecMock = mock(ChatClient.ChatClientRequestSpec.class);
			ChatClient.CallResponseSpec callResponseSpecMock = mock(ChatClient.CallResponseSpec.class);

			when(chatClientMock.prompt()).thenReturn(requestSpecMock);
			when(requestSpecMock.system(anyString())).thenReturn(requestSpecMock);

			when(requestSpecMock.user(any(Consumer.class))).thenAnswer(userInvocation -> {
				Consumer<ChatClient.PromptUserSpec> userSpecConsumer = userInvocation.getArgument(0);

				ChatClient.PromptUserSpec userSpecMock = mock(ChatClient.PromptUserSpec.class, RETURNS_DEEP_STUBS);

				when(userSpecMock.text(anyString())).thenReturn(userSpecMock);
				when(userSpecMock.param(anyString(), any())).thenReturn(userSpecMock);

				userSpecConsumer.accept(userSpecMock);

				return requestSpecMock;
			});

			when(requestSpecMock.call()).thenReturn(callResponseSpecMock);
			when(callResponseSpecMock.entity(DeveloperAnalystResult.class)).thenReturn(configuredResult);

			return executionLambda.apply(chatClientMock);
		});
	}

	@Given("the AI Gateway responds with profile summary {string} and title {string}")
	public void configureAiGatewayResponse(String profileSummary, String assignedTitle) {
		this.configuredResult = createDefaultResult(profileSummary, DeveloperTitle.valueOf(assignedTitle));
	}

	private DeveloperAnalystResult createDefaultResult(String summary, DeveloperTitle title) {
		return new DeveloperAnalystResult(
				summary,
				List.of("Quality 1"),
				List.of("Improvement 1"),
				title,
				80,
				85,
				90,
				88,
				"None",
				"Focus",
				List.of("Action 1"),
				"Growth",
				"Coaching",
				new DeveloperAnalystResult.KeyMetricInterpretations("Good", "Fast", "Active"),
				List.of("Risk 1")
		);
	}
}