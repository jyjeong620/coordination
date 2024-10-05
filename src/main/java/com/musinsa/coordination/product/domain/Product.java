package com.musinsa.coordination.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    private Long brandId;

    private BigDecimal price;

    protected Product() {
    }

    private Product(Long categoryId, Long brandId, BigDecimal price) {
        validate(price);
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
    }

    public static Product create(Long categoryId, Long brandId, BigDecimal price) {
        return new Product(categoryId, brandId, price);
    }

    private void validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다."); // TODO 에러 처리 추가
        }
    }
}
