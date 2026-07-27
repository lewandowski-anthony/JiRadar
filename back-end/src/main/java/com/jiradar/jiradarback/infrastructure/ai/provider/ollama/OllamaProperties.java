package com.jiradar.jiradarback.infrastructure.ai.provider.ollama;

import com.jiradar.jiradarback.infrastructure.ai.common.properties.AiProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "jiradar.ai.ollama")
public class OllamaProperties extends AiProperties {

	private final String baseUrl;

	@ConstructorBinding
	public OllamaProperties(String baseUrl, String modelName) {
		super(modelName);
		this.baseUrl = baseUrl;
	}
}