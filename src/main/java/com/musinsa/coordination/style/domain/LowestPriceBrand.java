package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.product.domain.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LowestPriceBrand {
    private final Brand brand;
    private final List<Product> products;
    private final BigDecimal totalPrice;

    protected LowestPriceBrand(Brand brand, List<Product> products, BigDecimal totalPrice) {
        this.brand = brand;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public String getBrandName() {
        return brand.getName();
    }
}
