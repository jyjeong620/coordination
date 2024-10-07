package com.musinsa.coordination.brand.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.common.util.StringConverter;
import com.musinsa.coordination.product.domain.Product;

public record ProductResponse(
        @JsonProperty("카테고리") String category,
        @JsonProperty("가격") String price
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getCategoryName(), StringConverter.convert(product.getPrice()));
    }
}
