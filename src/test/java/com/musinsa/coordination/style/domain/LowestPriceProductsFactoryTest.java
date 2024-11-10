package com.musinsa.coordination.style.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.Products;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LowestPriceProductsFactoryTest {

    @DisplayName("Product 리스트를 받아 카테고리별 가격이 가장 낮은 상품들을 생성한다.")
    @Test
    void create_one_category() {
        // given
        Category category = Category.create("카테고리1");
        Brand lowestPriceBrand = Brand.create("브랜드1");
        Brand middlePriceBrand = Brand.create("브랜드2");
        Brand highestPriceBrand = Brand.create("브랜드3");


        Product lowestPriceProduct = Product.create(category, lowestPriceBrand, BigDecimal.valueOf(100));
        Product middlePriceProduct = Product.create(category, middlePriceBrand, BigDecimal.valueOf(200));
        Product highestPriceProduct = Product.create(category, highestPriceBrand, BigDecimal.valueOf(300));

        // when
        Products lowestPriceProducts = LowestPriceProductsFactory.create(List.of(lowestPriceProduct, middlePriceProduct, highestPriceProduct));

        // then
        assertSoftly(softly -> {
            softly.assertThat(lowestPriceProducts.getProducts()).contains(lowestPriceProduct);
            softly.assertThat(lowestPriceProducts.getProducts()).doesNotContain(middlePriceProduct, highestPriceProduct);
        });
    }

    @DisplayName("Product 리스트를 받아 카테고리별 가격이 가장 낮은 상품들을 생성한다.")
    @Test
    void create_multiple_category() {
        // given
        Category category1 = Category.create("카테고리1");
        Category category2 = Category.create("카테고리2");
        Brand lowestPriceBrand = Brand.create("브랜드1");
        Brand middlePriceBrand = Brand.create("브랜드2");
        Brand highestPriceBrand = Brand.create("브랜드3");

        Product lowestPriceProduct1 = Product.create(category1, lowestPriceBrand, BigDecimal.valueOf(100));
        Product middlePriceProduct1 = Product.create(category1, middlePriceBrand, BigDecimal.valueOf(200));
        Product highestPriceProduct1 = Product.create(category1, highestPriceBrand, BigDecimal.valueOf(300));

        Product lowestPriceProduct2 = Product.create(category2, lowestPriceBrand, BigDecimal.valueOf(100));
        Product middlePriceProduct2 = Product.create(category2, middlePriceBrand, BigDecimal.valueOf(200));
        Product highestPriceProduct2 = Product.create(category2, highestPriceBrand, BigDecimal.valueOf(300));

        // when
        Products lowestPriceProducts = LowestPriceProductsFactory.create(List.of(lowestPriceProduct1, middlePriceProduct1, highestPriceProduct1, lowestPriceProduct2, middlePriceProduct2, highestPriceProduct2));

        // then
        assertSoftly(softly -> {
            softly.assertThat(lowestPriceProducts.getProducts()).contains(lowestPriceProduct1, lowestPriceProduct2);
            softly.assertThat(lowestPriceProducts.getProducts()).doesNotContain(middlePriceProduct1, highestPriceProduct1, middlePriceProduct2, highestPriceProduct2);
        });
    }
}