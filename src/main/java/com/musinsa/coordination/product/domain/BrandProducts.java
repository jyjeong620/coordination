package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.brand.domain.Brand;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class BrandProducts {

    private final Brand brand;
    private final List<Product> products;
    private final BigDecimal totalPrice;

    private BrandProducts(Brand brand, List<Product> products, BigDecimal totalPrice) {
        this.brand = brand;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public static BrandProducts of(Brand brand, List<Product> products) {
        BigDecimal totalPrice = calculateTotalPrice(products);
        return new BrandProducts(brand, products, totalPrice);
    }

    private static BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getBrandName() {
        return brand.getName();
    }
}
