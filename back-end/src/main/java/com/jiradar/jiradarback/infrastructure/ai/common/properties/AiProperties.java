package com.jiradar.jiradarback.infrastructure.ai.common.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class AiProperties {
    private String modelName;
}