package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@SQLRestriction("is_enable = true")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    private Long brandId;

    private BigDecimal price;

    private boolean isEnable;

    protected Product() {
    }

    private Product(Long categoryId, Long brandId, BigDecimal price, boolean isEnable) {
        validate(price);
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
        this.isEnable = isEnable;
    }

    public static Product create(Long categoryId, Long brandId, BigDecimal price) {
        return new Product(categoryId, brandId, price, true);
    }

    private void validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidRequestValueException("가격정보가 1원 미만입니다");
        }
    }

    public void update(Long categoryId, Long brandId, BigDecimal price) {
        validate(price);
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
    }

    public void disable() {
        this.isEnable = false;
    }
}
