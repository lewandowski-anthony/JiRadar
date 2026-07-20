package com.jiradar.jiradarback.infrastructure.ai.provider.ollama;

import com.jiradar.jiradarback.infrastructure.ai.common.properties.AiProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jiradar.ai.ollama")
public class OllamaProperties extends AiProperties {
	private String baseUrl;
}