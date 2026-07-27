package com.jiradar.jiradarback.infrastructure.ai.provider.gemini;

import com.jiradar.jiradarback.infrastructure.ai.common.properties.AiProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "jiradar.ai.gemini")
public class GeminiProperties extends AiProperties {
	
	private final String apiKey;

	@ConstructorBinding
	public GeminiProperties(String modelName, String apiKey) {
		super(modelName);
		this.apiKey = apiKey;
	}
}