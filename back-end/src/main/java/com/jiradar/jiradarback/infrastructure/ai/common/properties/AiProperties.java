package com.jiradar.jiradarback.infrastructure.ai.common.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AiProperties {
    private String modelName;
}