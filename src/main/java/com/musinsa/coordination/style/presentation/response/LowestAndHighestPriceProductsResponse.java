package com.musinsa.coordination.style.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.common.util.StringConverter;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProducts;

public record LowestAndHighestPriceProductsResponse(
        @JsonProperty("카테고리") String CategoryName,
        @JsonProperty("최저가") ProductResponse lowestPriceProduct,
        @JsonProperty("최고가") ProductResponse highestPriceProduct
) {

    public static LowestAndHighestPriceProductsResponse from(LowestAndHighestPriceProducts products) {
        return new LowestAndHighestPriceProductsResponse(
                products.getCategoryName(),
                ProductResponse.from(products.getLowestPriceProduct()),
                ProductResponse.from(products.getHighestPriceProduct())
        );
    }

    record ProductResponse(
            @JsonProperty("브랜드") String brand,
            @JsonProperty("가격") String price
    ) {

        public static ProductResponse from(Product product) {
            return new ProductResponse(product.getBrandName(), StringConverter.convert(product.getPrice()));
        }
    }
}
