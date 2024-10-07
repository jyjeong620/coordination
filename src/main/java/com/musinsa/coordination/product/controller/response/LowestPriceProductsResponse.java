package com.musinsa.coordination.product.controller.response;

import com.musinsa.coordination.product.domain.LowestPriceProducts;
import com.musinsa.coordination.product.domain.Product;

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

    record ProductResponse(String category, String brand, BigDecimal price) {
        public static ProductResponse from(Product product) {
            return new ProductResponse(product.getCategoryName(), product.getBrandName(), product.getPrice());
        }
    }
}
