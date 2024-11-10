package com.musinsa.coordination.style.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.coordination.common.util.StringConverter;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Product;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public record BrandProductsResponse(
        @JsonProperty("브랜드명") String brandName,
        @JsonProperty("카테고리") List<ProductResponse> products,
        @JsonProperty("총액") String totalPrice) {

    public static BrandProductsResponse from(BrandProducts lowestPriceBrand) {
        String brandName = lowestPriceBrand.getBrandName();
        List<ProductResponse> productResponses = lowestPriceBrand.getProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getCategoryId))
                .map(ProductResponse::from)
                .toList();
        BigDecimal totalPrice = lowestPriceBrand.getTotalPrice();

        return new BrandProductsResponse(brandName, productResponses, StringConverter.convert(totalPrice));
    }

    record ProductResponse(
            @JsonProperty("카테고리") String category,
            @JsonProperty("가격") String price
    ) {

        public static ProductResponse from(Product product) {
            return new ProductResponse(product.getCategoryName(), StringConverter.convert(product.getPrice()));
        }
    }

}
