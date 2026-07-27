package com.jiradar.jiradarback.infrastructure.ai;

import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.provider.gemini.GeminiAiConfig;
import com.jiradar.jiradarback.infrastructure.ai.provider.gemini.GeminiProperties;
import com.jiradar.jiradarback.infrastructure.ai.provider.ollama.OllamaAiConfig;
import com.jiradar.jiradarback.infrastructure.ai.provider.ollama.OllamaProperties;
import com.jiradar.jiradarback.infrastructure.ai.provider.vertex.VertexAiConfig;
import com.jiradar.jiradarback.infrastructure.ai.provider.vertex.VertexProperties;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AiProviderConfigTest {

	@Test
	void shouldBindGeminiPropertiesCorrectly() {
		GeminiProperties props = new GeminiProperties("gemini-1.5-pro", "secret-key");
		assertEquals("gemini-1.5-pro", props.getModelName());
		assertEquals("secret-key", props.getApiKey());
	}

	@Test
	void shouldBindOllamaPropertiesCorrectly() {
		OllamaProperties props = new OllamaProperties("http://localhost:11434", "llama3.1");
		assertEquals("http://localhost:11434", props.getBaseUrl());
		assertEquals("llama3.1", props.getModelName());
	}

	@Test
	void shouldBindVertexPropertiesCorrectly() {
		VertexProperties props = new VertexProperties("gcp-project", "europe-west1", "gemini-1.5-pro");
		assertEquals("gcp-project", props.getProjectId());
		assertEquals("europe-west1", props.getLocation());
		assertEquals("gemini-1.5-pro", props.getModelName());
	}

	@Test
	void shouldFormatDeveloperTitlesWithDescriptions() {
		String formatted = DeveloperTitle.getFormattedTitlesWithDescriptions();
		assertNotNull(formatted);
		assertTrue(formatted.contains("BUG_FIXER"));
		assertTrue(formatted.contains("SHIPPING_MACHINE"));
	}

	@Test
	void shouldLoadOllamaConfigWhenProviderIsOllama() {
		new ApplicationContextRunner()
				.withUserConfiguration(OllamaAiConfig.class, TestOllamaPropsConfig.class)
				.withPropertyValues(
						"jiradar.ai.provider=ollama",
						"jiradar.ai.ollama.base-url=http://localhost:11434",
						"jiradar.ai.ollama.model-name=llama3.1"
				)
				.run(context -> assertThat(context).hasSingleBean(ChatClient.class));
	}

	@Test
	void shouldLoadGeminiConfigWhenProviderIsGemini() {
		new ApplicationContextRunner()
				.withUserConfiguration(GeminiAiConfig.class, TestGeminiPropsConfig.class)
				.withPropertyValues(
						"jiradar.ai.provider=gemini",
						"jiradar.ai.gemini.api-key=dummy-key",
						"jiradar.ai.gemini.model-name=gemini-1.5-pro"
				)
				.run(context -> assertThat(context).hasSingleBean(ChatClient.class));
	}

	@Test
	void shouldLoadVertexConfigWhenProviderIsVertex() {
		new ApplicationContextRunner()
				.withUserConfiguration(VertexAiConfig.class, TestVertexPropsConfig.class)
				.withPropertyValues(
						"jiradar.ai.provider=vertex",
						"jiradar.ai.vertex.project-id=test-project",
						"jiradar.ai.vertex.location=europe-west1",
						"jiradar.ai.vertex.model-name=gemini-1.5-pro"
				)
				.run(context -> assertThat(context).hasSingleBean(ChatClient.class));
	}

	@Configuration
	@EnableConfigurationProperties(OllamaProperties.class)
	static class TestOllamaPropsConfig {}

	@Configuration
	@EnableConfigurationProperties(GeminiProperties.class)
	static class TestGeminiPropsConfig {}

	@Configuration
	@EnableConfigurationProperties(VertexProperties.class)
	static class TestVertexPropsConfig {}
}