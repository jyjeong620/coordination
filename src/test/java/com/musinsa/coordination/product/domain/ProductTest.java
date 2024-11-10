package com.musinsa.coordination.product.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("카테고리, 브랜드, 가격을 이용하여 Product 객체를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Category category = Category.create("상의");
        Brand brand = Brand.create("나이키");
        BigDecimal price = BigDecimal.valueOf(10000);

        // when
        Product product = Product.create(category, brand, price);

        // then
        assertSoftly(softly -> {
            softly.assertThat(product).isNotNull();
            softly.assertThat(product.getCategory()).isEqualTo(category);
            softly.assertThat(product.getBrand()).isEqualTo(brand);
            softly.assertThat(product.getPrice()).isEqualTo(price);
        });
    }

    @DisplayName("가격이 1원 미만일 경우 예외를 발생시킨다.")
    @Test
    void create_InvalidPrice() {
        // given
        Category category = Category.create("상의");
        Brand brand = Brand.create("나이키");
        BigDecimal price = BigDecimal.ZERO;

        // when & then
        assertThatThrownBy(() -> Product.create(category, brand, price))
                .isInstanceOf(InvalidRequestValueException.class)
                .hasMessage("가격정보가 1원 미만입니다");
    }

    @DisplayName("Product 객체의 카테고리, 브랜드, 가격을 수정할 수 있다.")
    @Test
    void update() {
        // given
        Category category = Category.create("상의");
        Brand brand = Brand.create("나이키");
        BigDecimal price = BigDecimal.valueOf(10000);
        Product product = Product.create(category, brand, price);

        Category updatedCategory = Category.create("하의");
        Brand updatedBrand = Brand.create("아디다스");
        BigDecimal updatedPrice = BigDecimal.valueOf(20000);

        // when
        product.update(updatedCategory, updatedBrand, updatedPrice);

        // then
        assertSoftly(softly -> {
            softly.assertThat(product.getCategory()).isEqualTo(updatedCategory);
            softly.assertThat(product.getBrand()).isEqualTo(updatedBrand);
            softly.assertThat(product.getPrice()).isEqualTo(updatedPrice);
        });
    }
}