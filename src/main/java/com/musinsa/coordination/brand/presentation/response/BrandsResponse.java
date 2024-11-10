package com.musinsa.coordination.brand.presentation.response;

import com.musinsa.coordination.brand.domain.Brand;
import java.util.List;

public record BrandsResponse(List<BrandResponse> brands) {

    public static BrandsResponse from(List<Brand> brands) {
        List<BrandResponse> response = brands.stream()
            .map(BrandResponse::from)
            .toList();
        return new BrandsResponse(response);
    }
}
