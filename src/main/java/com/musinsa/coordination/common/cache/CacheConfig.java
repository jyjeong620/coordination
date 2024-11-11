package com.musinsa.coordination.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager caffeinCacheManager() {
        List<CaffeineCache> caffeineCaches = Arrays.stream(CacheType.values())
                .map(CacheConfig::createCaffeineCache)
                .toList();

        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caffeineCaches);
        return simpleCacheManager;
    }

    private static CaffeineCache createCaffeineCache(CacheType cacheType) {
        return new CaffeineCache(
                cacheType.getCacheName(),
                Caffeine.newBuilder()
                        .expireAfterWrite(cacheType.getTtl())
                        .build());
    }
}
