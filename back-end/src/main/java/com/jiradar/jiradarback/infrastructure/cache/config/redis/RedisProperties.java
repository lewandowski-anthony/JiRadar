package com.jiradar.jiradarback.infrastructure.cache.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jiradar.cache.redis")
public record RedisProperties(
		Integer database,
		String host,
		Integer port,
		String username,
		String password
) {}