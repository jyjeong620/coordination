package com.musinsa.coordination.style.presentation.response;

import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.Products;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public record LowestPriceProductsResponse(BigDecimal totalPrice, List<ProductResponse> products) {

    public static LowestPriceProductsResponse from(Products products) {
        List<ProductResponse> productResponses = products.getProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getCategoryId))
                .map(ProductResponse::from)
                .toList();

        return new LowestPriceProductsResponse(products.getTotalPrice(), productResponses);
    }

    record ProductResponse(String category, String brand, BigDecimal price) {
        public static ProductResponse from(Product product) {
            return new ProductResponse(product.getCategoryName(), product.getBrandName(), product.getPrice());
        }
    }
}
