package com.jiradar.jiradarback.infrastructure.ai.provider.vertex;

import com.jiradar.jiradarback.infrastructure.ai.common.properties.AiProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jiradar.ai.vertex")
public class VertexProperties extends AiProperties {
	private String projectId;
	private String location;
}