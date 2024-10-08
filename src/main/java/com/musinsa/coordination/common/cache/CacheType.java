package com.musinsa.coordination.common.cache;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum CacheType {
    LOWEST_PRICE_PRODUCTS("lowestPriceProducts", Duration.ofMinutes(1L)),
    ;

    private final String cacheName;
    private final Duration ttl;

    CacheType(String cacheName, Duration ttl) {
        this.cacheName = cacheName;
        this.ttl = ttl;
    }
}
