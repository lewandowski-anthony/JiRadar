package com.jiradar.jiradarback.infrastructure.cache;

import com.jiradar.jiradarback.infrastructure.cache.config.CacheProperties;
import com.jiradar.jiradarback.infrastructure.cache.config.redis.RedisProperties;
import com.jiradar.jiradarback.infrastructure.cache.strategy.CaffeineCacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.strategy.NoOpCacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.strategy.RedisCacheProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.cache.autoconfigure.CacheAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CacheProviderTest {

	private final ApplicationContextRunner baseRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					ConfigurationPropertiesAutoConfiguration.class,
					CacheAutoConfiguration.class
			))
			.withUserConfiguration(
					CacheProperties.class,
					CaffeineCacheProvider.class,
					RedisCacheProvider.class,
					NoOpCacheProvider.class,
					MockRedisTestConfig.class
			);

	@Test
	void shouldLoadCaffeineByDefault() {
		baseRunner.run(context -> {
			assertThat(context).hasSingleBean(CaffeineCacheProvider.class);
			assertThat(context).doesNotHaveBean(RedisCacheProvider.class);
			assertThat(context).doesNotHaveBean(NoOpCacheProvider.class);

			CaffeineCacheProvider provider = context.getBean(CaffeineCacheProvider.class);
			assertThat(provider.getName()).isEqualTo("caffeine");
			CacheManager cacheManager = provider.buildCacheManager();
			assertThat(cacheManager).isNotNull();
		});
	}

	@Test
	void shouldLoadRedisWhenConfigured() {
		baseRunner
				.withUserConfiguration(RedisPropertiesRegistrar.class)
				.withPropertyValues(
						"jiradar.cache.provider=redis",
						"jiradar.cache.enabled=true",
						"jiradar.cache.redis.uri=redis://localhost:6379",
						"jiradar.cache.redis.host=localhost",
						"jiradar.cache.redis.port=6379",
						"jiradar.cache.redis.database=0"
				)
				.run(context -> {
					assertThat(context).hasSingleBean(RedisCacheProvider.class);
					assertThat(context).doesNotHaveBean(CaffeineCacheProvider.class);
					assertThat(context).doesNotHaveBean(NoOpCacheProvider.class);

					RedisCacheProvider provider = context.getBean(RedisCacheProvider.class);
					assertThat(provider.getName()).isEqualTo("redis");
					CacheManager cacheManager = provider.buildCacheManager();
					assertThat(cacheManager).isNotNull();
				});
	}

	@Test
	void shouldLoadNoOpWhenCacheIsDisabled() {
		baseRunner
				.withPropertyValues("jiradar.cache.enabled=false")
				.run(context -> {
					assertThat(context).hasSingleBean(NoOpCacheProvider.class);
					assertThat(context).doesNotHaveBean(CaffeineCacheProvider.class);
					assertThat(context).doesNotHaveBean(RedisCacheProvider.class);

					NoOpCacheProvider provider = context.getBean(NoOpCacheProvider.class);
					assertThat(provider.getName()).isEqualTo("none");
					CacheManager cacheManager = provider.buildCacheManager();
					assertThat(cacheManager).isNotNull();
				});
	}

	@Configuration
	@EnableConfigurationProperties(RedisProperties.class)
	static class RedisPropertiesRegistrar {}

	@Configuration
	static class MockRedisTestConfig {
		@Bean
		public RedisConnectionFactory redisConnectionFactory() {
			return mock(RedisConnectionFactory.class);
		}
	}
}