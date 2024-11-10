package com.musinsa.coordination.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrandProductsTest {

    @DisplayName("브랜드와 상품정보를 이용하여 브랜드 상품 객체를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Brand brand = Brand.create("나이키");
        Category category = Category.create("카테고리");
        Product product1 = Product.create(category, brand, BigDecimal.valueOf(10000));
        Product product2 = Product.create(category, brand, BigDecimal.valueOf(20000));
        List<Product> products = List.of(product1, product2);
        // when
        BrandProducts brandProducts = BrandProducts.of(brand, products);

        // then
        assertSoftly(softly -> {
            softly.assertThat(brandProducts).isNotNull();
            softly.assertThat(brandProducts.getBrand()).isEqualTo(brand);
            softly.assertThat(brandProducts.getProducts()).contains(product1, product2);
        });
    }

    @DisplayName("브랜드 상품의 총액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice() {
        // given
        Brand brand = Brand.create("나이키");
        Category category = Category.create("카테고리");
        BigDecimal price1 = BigDecimal.valueOf(20000);
        BigDecimal price2 = BigDecimal.valueOf(10000);

        Product product1 = Product.create(category, brand, price1);
        Product product2 = Product.create(category, brand, price2);
        List<Product> products = List.of(product1, product2);
        BrandProducts brandProducts = BrandProducts.of(brand, products);

        // when
        BigDecimal totalPrice = brandProducts.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(BigDecimal.valueOf(30000));
    }
}