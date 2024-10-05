package com.musinsa.coordination.product.controller.response;

import com.musinsa.coordination.product.domain.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, Long categoryId, Long brandId, BigDecimal price) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getCategoryId(), product.getBrandId(), product.getPrice());
    }
}
