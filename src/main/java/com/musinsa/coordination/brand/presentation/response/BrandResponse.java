package com.musinsa.coordination.brand.presentation.response;

import com.musinsa.coordination.brand.domain.Brand;

public record BrandResponse(Long id, String name) {

    public static BrandResponse from(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName());
    }
}
