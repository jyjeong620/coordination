package com.musinsa.coordination.style.domain;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LowestPriceBrandFactoryTest {

    @DisplayName("Product 리스트중 가장 낮은 가격을 가진 브랜드를 생성한다.")
    @Test
    void create() {
        // given
        Brand lowestBrand = Brand.create("저렴한 브랜드");
        Brand highestBrand = Brand.create("비싼 브랜드");

        Category category1 = Category.create("카테고리1");
        Category category2 = Category.create("카테고리2");
        Category category3 = Category.create("카테고리3");

        Product product1 = Product.create(category1, lowestBrand, BigDecimal.valueOf(100));
        Product product2 = Product.create(category2, lowestBrand, BigDecimal.valueOf(100));
        Product product3 = Product.create(category3, lowestBrand, BigDecimal.valueOf(100));
        Product product4 = Product.create(category1, highestBrand, BigDecimal.valueOf(200));
        Product product5 = Product.create(category2, highestBrand, BigDecimal.valueOf(200));
        Product product6 = Product.create(category2, highestBrand, BigDecimal.valueOf(200));

        // when
        BrandProducts lowestPriceBrand = LowestPriceBrandFactory.create(List.of(product1, product2, product3, product4, product5, product6));

        // then
        assertSoftly(softly -> {
            softly.assertThat(lowestPriceBrand.getBrand()).isEqualTo(lowestBrand);
            softly.assertThat(lowestPriceBrand.getProducts()).contains(product1, product2, product3);
        });
    }
}