package com.jiradar.jiradarback.infrastructure.cache.config;

import com.jiradar.jiradarback.infrastructure.cache.CacheProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@Profile("!test")
public class CacheConfig {

	private final CacheProvider activeCacheProvider;

	@Bean
	public CacheManager cacheManager() {
		return activeCacheProvider.buildCacheManager();
	}
}