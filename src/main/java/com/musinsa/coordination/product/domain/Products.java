package com.musinsa.coordination.product.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class Products {

    private final BigDecimal totalPrice;
    private final List<Product> products;

    private Products(BigDecimal totalPrice, List<Product> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public static Products from(List<Product> products) {
        BigDecimal totalPrice = calculateTotalPrice(products);
        return new Products(totalPrice, products);
    }

    private static BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}