package com.jiradar.jiradarback.infrastructure.ai;

import com.jiradar.jiradarback.infrastructure.ai.common.model.enums.DeveloperTitle;
import com.jiradar.jiradarback.infrastructure.ai.provider.gemini.GeminiProperties;
import com.jiradar.jiradarback.infrastructure.ai.provider.ollama.OllamaProperties;
import com.jiradar.jiradarback.infrastructure.ai.provider.vertex.VertexProperties;
import org.junit.jupiter.api.Test;

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
}