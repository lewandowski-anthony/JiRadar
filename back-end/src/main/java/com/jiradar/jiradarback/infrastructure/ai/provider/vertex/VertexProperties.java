package com.jiradar.jiradarback.infrastructure.ai.provider.vertex;

import com.jiradar.jiradarback.infrastructure.ai.common.properties.AiProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "jiradar.ai.vertex")
public class VertexProperties extends AiProperties {

	private final String projectId;
	private final String location;

	@ConstructorBinding
	public VertexProperties(String projectId, String location, String modelName) {
		super(modelName);
		this.projectId = projectId;
		this.location = location;
	}
}