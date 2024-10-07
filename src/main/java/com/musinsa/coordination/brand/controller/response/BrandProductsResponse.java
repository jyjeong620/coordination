package com.musinsa.coordination.brand.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.brand.domain.LowestPriceBrand;
import com.musinsa.coordination.common.util.StringConverter;
import com.musinsa.coordination.product.domain.Product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public record BrandProductsResponse(
        @JsonProperty("브랜드명") String brandName,
        @JsonProperty("카테고리") List<ProductResponse> products,
        @JsonProperty("총액") String totalPrice) {

    public static BrandProductsResponse from(LowestPriceBrand lowestPriceBrand) {
        String brandName = lowestPriceBrand.getBrandName();
        List<ProductResponse> productResponses = lowestPriceBrand.getProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getCategoryId))
                .map(ProductResponse::from)
                .toList();
        BigDecimal totalPrice = lowestPriceBrand.getTotalPrice();

        return new BrandProductsResponse(brandName, productResponses, StringConverter.convert(totalPrice));
    }
}
