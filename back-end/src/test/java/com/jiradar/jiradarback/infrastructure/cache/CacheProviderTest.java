package com.jiradar.jiradarback.infrastructure.cache;

import com.jiradar.jiradarback.infrastructure.cache.config.CacheProperties;
import com.jiradar.jiradarback.infrastructure.cache.strategy.CaffeineCacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.strategy.NoOpCacheProvider;
import com.jiradar.jiradarback.infrastructure.cache.strategy.RedisCacheProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.cache.autoconfigure.CacheAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CacheProviderTest {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(
					CacheProperties.class,
					CaffeineCacheProvider.class,
					RedisCacheProvider.class,
					NoOpCacheProvider.class,
					MockRedisTestConfig.class
			)
			.withUserConfiguration(CacheAutoConfiguration.class);

	@Test
	void shouldLoadCaffeineByDefault() {
		contextRunner.run(context -> {
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
		contextRunner.withPropertyValues("jiradar.cache.provider=redis")
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
		contextRunner.withPropertyValues("jiradar.cache.enabled=false")
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
	static class MockRedisTestConfig {
		@Bean
		public RedisConnectionFactory redisConnectionFactory() {
			return mock(RedisConnectionFactory.class);
		}
	}
}