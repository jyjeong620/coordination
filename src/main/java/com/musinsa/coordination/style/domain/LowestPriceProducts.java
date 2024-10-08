package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.product.domain.Product;
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