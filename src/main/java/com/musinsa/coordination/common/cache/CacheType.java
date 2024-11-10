package com.musinsa.coordination.common.cache;

import java.time.Duration;
import lombok.Getter;

@Getter
public enum CacheType {
    LOWEST_PRICE_PRODUCTS("lowestPriceProducts", Duration.ofSeconds(1L)),
    ;

    private final String cacheName;
    private final Duration ttl;

    CacheType(String cacheName, Duration ttl) {
        this.cacheName = cacheName;
        this.ttl = ttl;
    }
}
