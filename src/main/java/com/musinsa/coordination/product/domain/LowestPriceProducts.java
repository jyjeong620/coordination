package com.musinsa.coordination.product.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LowestPriceProducts {
    private BigDecimal totalPrice;
    private List<Product> products;

    private LowestPriceProducts(BigDecimal totalPrice, List<Product> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public static LowestPriceProducts from(List<Product> products) {
        BigDecimal totalPrice = calculateTotalPrice(products);

        return new LowestPriceProducts(totalPrice, products);
    }

    private static BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
