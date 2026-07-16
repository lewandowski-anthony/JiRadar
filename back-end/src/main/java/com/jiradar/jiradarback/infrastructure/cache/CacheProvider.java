package com.jiradar.jiradarback.infrastructure.cache;

import org.springframework.cache.CacheManager;

public interface CacheProvider {
    String getName();
    CacheManager buildCacheManager();
}