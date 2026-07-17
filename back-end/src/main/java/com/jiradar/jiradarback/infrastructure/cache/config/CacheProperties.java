package com.jiradar.jiradarback.infrastructure.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

	private Map<String, CacheSpec> specs = new HashMap<>();

	public Duration resolveTtl(AvailableCache cache) {
		CacheSpec spec = specs.get(cache.name());
		if (spec != null && spec.getTtl() != null) {
			return spec.getTtl();
		}
		long millis = cache.getCacheRetentionTimeUnit().toMillis(cache.getCacheRetentionTime());
		return Duration.ofMillis(millis);
	}

	public int resolveMaxSize(AvailableCache cache) {
		CacheSpec spec = specs.get(cache.name());
		if (spec != null && spec.getMaxSize() != null) {
			return spec.getMaxSize();
		}
		return 500;
	}

	@Getter
	@Setter
	public static class CacheSpec {
		private Duration ttl;
		private Integer maxSize;
	}
}