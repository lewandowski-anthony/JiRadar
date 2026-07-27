package com.jiradar.jiradarback.config;

import com.jiradar.jiradarback.infrastructure.ai.gateway.AiPromptGateway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class AiTestConfig {

	@Bean
	@Primary
	public AiPromptGateway aiPromptGateway() {
		return mock(AiPromptGateway.class);
	}
}