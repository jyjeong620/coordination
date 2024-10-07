package com.musinsa.coordination.product.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LowestPriceProducts {
    private final BigDecimal totalPrice;
    private final List<Product> products;

    protected LowestPriceProducts(BigDecimal totalPrice, List<Product> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }
}