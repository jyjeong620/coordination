package com.musinsa.coordination.product.presentation.response;

import com.musinsa.coordination.product.domain.Product;

import java.util.List;

public record ProductsResponse(List<ProductResponse> products) {

    public static ProductsResponse from(List<Product> products) {
        return new ProductsResponse(products.stream()
                .map(ProductResponse::from)
                .toList());
    }
}
