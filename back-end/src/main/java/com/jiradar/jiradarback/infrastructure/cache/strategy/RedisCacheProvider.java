package com.jiradar.jiradarback.infrastructure.cache.strategy;

import com.jiradar.jiradarback.infrastructure.cache.CacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.config.AvailableCache;
import com.jiradar.jiradarback.infrastructure.cache.config.CacheProperties;
import com.jiradar.jiradarback.infrastructure.cache.config.redis.RedisProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "jiradar.cache.provider", havingValue = "redis")
@ConditionalOnProperty(name = "jiradar.cache.enabled", havingValue = "true", matchIfMissing = true)
public class RedisCacheProvider implements CacheProvider {

	private final RedisProperties redisProperties;
	private final CacheProperties cacheProperties;

	@Override
	public String getName() {
		return "redis";
	}

	@PostConstruct
	public void validate() {
		if (StringUtils.isBlank(redisProperties.host())) {
			throw new IllegalArgumentException("Property 'jiradar.cache.redis.host' must be defined and not empty when cache provider is 'redis'.");
		}
		if (redisProperties.port() <= 0) {
			throw new IllegalArgumentException("Property 'jiradar.cache.redis.port' must be defined and greater than 0 when cache provider is 'redis'.");
		}
		if (redisProperties.database() < 0) {
			throw new IllegalArgumentException("Property 'jiradar.cache.redis.database' must be defined and greater than or equal to 0 when cache provider is 'redis'.");
		}
	}

	@Override
	public CacheManager buildCacheManager() {

		LettuceConnectionFactory connectionFactory = getConnectionFactory();

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

	private LettuceConnectionFactory getConnectionFactory() {

		RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
		standaloneConfig.setHostName(this.redisProperties.host());
		standaloneConfig.setPort(this.redisProperties.port());
		if (StringUtils.isNotBlank(this.redisProperties.password())) {
			standaloneConfig.setPassword(this.redisProperties.password());
		}
		if (StringUtils.isNotBlank(this.redisProperties.username())) {
			standaloneConfig.setUsername(this.redisProperties.username());
		}
		standaloneConfig.setDatabase(this.redisProperties.database());

		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfig);
		connectionFactory.afterPropertiesSet();
		connectionFactory.start();
		return connectionFactory;
	}
}