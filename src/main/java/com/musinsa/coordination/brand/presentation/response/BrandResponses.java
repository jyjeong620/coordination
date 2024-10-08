package com.musinsa.coordination.brand.presentation.response;

import com.musinsa.coordination.brand.domain.Brand;

import java.util.List;

public record BrandResponses(List<BrandResponse> brands) {

    public static BrandResponses from(List<Brand> brands) {
        return new BrandResponses(brands.stream()
                .map(BrandResponse::from)
                .toList());
    }
}
