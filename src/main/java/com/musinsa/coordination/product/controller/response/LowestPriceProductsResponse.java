package com.musinsa.coordination.product.controller.response;

import com.musinsa.coordination.product.domain.LowestPriceProducts;

import java.math.BigDecimal;
import java.util.List;

public record LowestPriceProductsResponse(BigDecimal totalPrice, List<ProductResponse> products) {

    public static LowestPriceProductsResponse from(LowestPriceProducts products) {
        List<ProductResponse> productResponses = products.getProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();

        return new LowestPriceProductsResponse(products.getTotalPrice(), productResponses);
    }
}
