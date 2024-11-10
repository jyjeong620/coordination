package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@SQLRestriction("is_enable = true")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private BigDecimal price;

    private boolean isEnable;

    protected Product() {
    }

    private Product(Category category, Brand brand, BigDecimal price, boolean isEnable) {
        validate(price);
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.isEnable = isEnable;
    }

    public static Product create(Category category, Brand brand, BigDecimal price) {
        return new Product(category, brand, price, true);
    }

    private void validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidRequestValueException("가격정보가 1원 미만입니다");
        }
    }

    public void update(Category category, Brand brand, BigDecimal price) {
        validate(price);
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public void disable() {
        this.isEnable = false;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Long getCategoryId() {
        return category.getId();
    }

    public String getBrandName() {
        return brand.getName();
    }
}
