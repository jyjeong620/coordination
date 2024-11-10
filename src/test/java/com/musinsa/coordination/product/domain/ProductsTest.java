package com.musinsa.coordination.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsTest {

    @DisplayName("상품정보를 이용하여 Products 객체를 생성할 수 있다.")
    @Test
    void create() {
        // given
        Category category = Category.create("카테고리");
        Brand brand = Brand.create("brand");
        Product product1 = Product.create(category, brand, BigDecimal.valueOf(10000));
        Product product2 = Product.create(category, brand, BigDecimal.valueOf(10000));
        List<Product> productList = List.of(product1, product2);

        // when
        Products products = Products.from(productList);

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).isNotNull();
            softly.assertThat(products.getProducts()).contains(product1, product2);
        });
    }

    @DisplayName("Products 객체의 총액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice() {
        // given
        Category category = Category.create("카테고리");
        Brand brand = Brand.create("brand");
        BigDecimal price1 = BigDecimal.valueOf(20000);
        BigDecimal price2 = BigDecimal.valueOf(10000);

        Product product1 = Product.create(category, brand, price1);
        Product product2 = Product.create(category, brand, price2);
        List<Product> products = List.of(product1, product2);
        Products productsList = Products.from(products);

        // when
        BigDecimal totalPrice = productsList.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(BigDecimal.valueOf(30000));
    }
}