package com.musinsa.coordination.style.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.product.domain.BrandProducts;

public record LowestPriceBrandResponse(@JsonProperty("최저가") BrandProductsResponse brandProducts) {
    public static LowestPriceBrandResponse from(BrandProducts lowestPriceBrand) {
        BrandProductsResponse brandProducts = BrandProductsResponse.from(lowestPriceBrand);
        return new LowestPriceBrandResponse(brandProducts);
    }
}


