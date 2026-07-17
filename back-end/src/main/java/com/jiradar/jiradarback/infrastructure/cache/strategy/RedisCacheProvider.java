package com.jiradar.jiradarback.infrastructure.cache.strategy;

import com.jiradar.jiradarback.infrastructure.cache.CacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.cache.config.CacheProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "cache.provider", havingValue = "redis")
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true", matchIfMissing = true)
public class RedisCacheProvider implements CacheProvider {

	@Lazy
	private final RedisConnectionFactory connectionFactory;
	private final CacheProperties cacheProperties;

	@Override
	public String getName() {
		return "redis";
	}

	@Override
	public CacheManager buildCacheManager() {
		RedisSerializer<Object> jsonSerializer = RedisSerializer.json();

		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
				.defaultCacheConfig()
				.disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

		for (AvailableCache cache : AvailableCache.values()) {
			Duration ttl = cacheProperties.resolveTtl(cache);
			cacheConfigurations.put(cache.name(), defaultConfig.entryTtl(ttl));
		}

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(defaultConfig)
				.withInitialCacheConfigurations(cacheConfigurations)
				.build();
	}
}