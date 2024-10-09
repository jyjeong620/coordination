package com.musinsa.coordination.product.infrastructure;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.infrastructure.BrandJpaRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.infrastructure.CategoryJpaRepository;
import com.musinsa.coordination.product.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;
    @Autowired
    private BrandJpaRepository brandRepository;

    @DisplayName("브랜드와 카테고리가 같은 상품중 가격이 가장 낮은 상품을 조회한다.")
    @Test
    void findAllLowestPriceProducts() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("나이키"));
        Product lowestProduct = Product.create(category, brand, BigDecimal.valueOf(100));
        Product highestProduct = Product.create(category, brand, BigDecimal.valueOf(1000));
        productRepository.save(lowestProduct);
        productRepository.save(highestProduct);

        // when
        List<Product> products = productRepository.findAllLowestPriceProducts();

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).hasSize(1);
            softly.assertThat(products.getFirst()).isEqualTo(lowestProduct);
        });
    }
}