package com.jiradar.jiradarback.infrastructure.cache.strategy;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiradar.jiradarback.infrastructure.cache.CacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.cache.config.CacheProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cache.provider", havingValue = "caffeine", matchIfMissing = true)
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true", matchIfMissing = true)
public class CaffeineCacheProvider implements CacheProvider {

	private final CacheProperties cacheProperties;

	@Override
	public String getName() {
		return "caffeine";
	}

	@Override
	public CacheManager buildCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();

		for (AvailableCache cache : AvailableCache.values()) {
			Duration ttl = cacheProperties.resolveTtl(cache);
			int maxSize = cacheProperties.resolveMaxSize(cache);

			cacheManager.registerCustomCache(cache.name(),
					Caffeine.newBuilder()
							.expireAfterWrite(ttl.toMillis(), TimeUnit.MILLISECONDS)
							.maximumSize(maxSize)
							.build());
		}
		return cacheManager;
	}
}