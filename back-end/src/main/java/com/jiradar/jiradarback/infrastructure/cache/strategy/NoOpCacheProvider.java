package com.jiradar.jiradarback.infrastructure.cache.strategy;

import com.jiradar.jiradarback.infrastructure.cache.CacheProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "jiradar.cache.enabled", havingValue = "false")
public class NoOpCacheProvider implements CacheProvider {

	@Override
	public String getName() {
		return "none";
	}

	@Override
	public CacheManager buildCacheManager() {
		return new NoOpCacheManager();
	}
}