package com.musinsa.coordination.style.domain;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LowestAndHighestPriceProductsFactoryTest {

    @DisplayName("상품정보중 가격이 가장 낮은 상품과 가격이 가장 높은 상품을 가진 LowestAndHighestPriceProducts를 생성한다.")
    @Test
    void create() {
        // given
        Category category = Category.create("카테고리");
        Brand highestBrand = Brand.create("최고가 브랜드");
        Brand lowestBrand = Brand.create("최저가 브랜드");
        Brand middleBrand = Brand.create("중간 브랜드");

        BigDecimal highestPrice = BigDecimal.valueOf(10000);
        BigDecimal lowestPrice = BigDecimal.valueOf(100);
        BigDecimal middlePrice = BigDecimal.valueOf(1000);

        Product highestProduct = Product.create(category, highestBrand, highestPrice);
        Product lowestProduct = Product.create(category, lowestBrand, lowestPrice);
        Product middleProduct = Product.create(category, middleBrand, middlePrice);

        // when
        LowestAndHighestPriceProducts products = LowestAndHighestPriceProductsFactory.create(category, List.of(highestProduct, lowestProduct, middleProduct));

        // then
        assertSoftly(softly -> {
            softly.assertThat(products.getCategory()).isEqualTo(category);
            softly.assertThat(products.getLowestPriceProduct()).isEqualTo(lowestProduct);
            softly.assertThat(products.getHighestPriceProduct()).isEqualTo(highestProduct);
        });
    }
}