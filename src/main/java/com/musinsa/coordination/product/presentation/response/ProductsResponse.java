package com.musinsa.coordination.product.presentation.response;

import com.musinsa.coordination.product.domain.Product;
import java.util.List;

public record ProductsResponse(List<ProductResponse> products) {

    public static ProductsResponse from(List<Product> products) {
        List<ProductResponse> responses = products.stream()
            .map(ProductResponse::from)
            .toList();
        return new ProductsResponse(responses);
    }
}
