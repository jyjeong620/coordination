package com.musinsa.coordination.brand.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.brand.domain.LowestPriceBrand;

public record LowestPriceBrandResponse(@JsonProperty("최저가") BrandProductsResponse brandProducts) {
    public static LowestPriceBrandResponse from(LowestPriceBrand lowestPriceBrand) {
        BrandProductsResponse brandProducts = BrandProductsResponse.from(lowestPriceBrand);
        return new LowestPriceBrandResponse(brandProducts);
    }
}


