package com.musinsa.coordination.product.presentation.response;

import com.musinsa.coordination.product.domain.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String category, String brand, BigDecimal price) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getCategoryName(), product.getBrandName(), product.getPrice());
    }
}
