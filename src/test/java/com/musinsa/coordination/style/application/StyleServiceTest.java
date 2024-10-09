package com.musinsa.coordination.style.application;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.mock.FakeBrandRepository;
import com.musinsa.coordination.mock.FakeCategoryRepository;
import com.musinsa.coordination.mock.FakeProductRepository;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.Products;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProducts;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StyleServiceTest {

    private StyleService styleService;
    private FakeProductRepository productRepository;
    private FakeCategoryRepository categoryRepository;
    private FakeBrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        categoryRepository = new FakeCategoryRepository();
        brandRepository = new FakeBrandRepository();
        styleService = new StyleService(productRepository, categoryRepository);
    }

    @DisplayName("카테고리 별로 최저가격인 브랜드와 가격과 총액을 응답한다.")
    @Test
    void getLowestPriceProductsByCategory() {
        // given
        Category category1 = categoryRepository.save(Category.create("상의"));
        Category category2 = categoryRepository.save(Category.create("하의"));
        Category category3 = categoryRepository.save(Category.create("신발"));
        Brand brand1 = brandRepository.save(Brand.create("A"));
        Brand brand2 = brandRepository.save(Brand.create("B"));
        Product lowestProduct1 = Product.create(category1, brand1, BigDecimal.valueOf(100));
        Product lowestProduct2 = Product.create(category2, brand1, BigDecimal.valueOf(100));
        Product lowestProduct3 = Product.create(category3, brand1, BigDecimal.valueOf(100));
        Product highestProduct1 = Product.create(category1, brand2, BigDecimal.valueOf(1000));
        Product highestProduct2 = Product.create(category2, brand2, BigDecimal.valueOf(1000));
        Product highestProduct3 = Product.create(category3, brand2, BigDecimal.valueOf(1000));
        productRepository.save(lowestProduct1);
        productRepository.save(lowestProduct2);
        productRepository.save(lowestProduct3);
        productRepository.save(highestProduct1);
        productRepository.save(highestProduct2);
        productRepository.save(highestProduct3);

        // when
        Products products = styleService.getLowestPriceProductsByCategory();

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).isNotNull();
            softly.assertThat(products.getProducts()).hasSize(3);
            softly.assertThat(products.getTotalPrice()).isEqualTo(BigDecimal.valueOf(300));
        });
    }

    @DisplayName("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드를 응답한다")
    @Test
    void getLowestPriceBrand() {
        // given
        Category category1 = categoryRepository.save(Category.create("상의"));
        Category category2 = categoryRepository.save(Category.create("하의"));
        Category category3 = categoryRepository.save(Category.create("신발"));
        Brand brand1 = brandRepository.save(Brand.create("A"));
        Brand brand2 = brandRepository.save(Brand.create("B"));
        Product lowestProduct1 = Product.create(category1, brand1, BigDecimal.valueOf(100));
        Product lowestProduct2 = Product.create(category2, brand1, BigDecimal.valueOf(100));
        Product lowestProduct3 = Product.create(category3, brand1, BigDecimal.valueOf(100));
        Product highestProduct1 = Product.create(category1, brand2, BigDecimal.valueOf(1000));
        Product highestProduct2 = Product.create(category2, brand2, BigDecimal.valueOf(1000));
        Product highestProduct3 = Product.create(category3, brand2, BigDecimal.valueOf(1000));
        productRepository.save(lowestProduct1);
        productRepository.save(lowestProduct2);
        productRepository.save(lowestProduct3);
        productRepository.save(highestProduct1);
        productRepository.save(highestProduct2);
        productRepository.save(highestProduct3);

        // when
        BrandProducts products = styleService.getLowestPriceBrand();

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).isNotNull();
            softly.assertThat(products.getBrandName()).isEqualTo("A");
            softly.assertThat(products.getTotalPrice()).isEqualTo(BigDecimal.valueOf(300));
        });
    }

    @DisplayName("요청한 카테고리의 최저가 상품과 최고가 상품을 응답한다.")
    @Test
    void getLowestAndHighestPriceProductsBy() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("A"));
        Product lowestProduct = Product.create(category, brand, BigDecimal.valueOf(100));
        Product middleProduct = Product.create(category, brand, BigDecimal.valueOf(500));
        Product highestProduct = Product.create(category, brand, BigDecimal.valueOf(1000));
        productRepository.save(lowestProduct);
        productRepository.save(middleProduct);
        productRepository.save(highestProduct);

        // when
        LowestAndHighestPriceProducts products = styleService.getLowestAndHighestPriceProductsBy(category.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).isNotNull();
            softly.assertThat(products.getCategoryName()).isEqualTo("상의");
            softly.assertThat(products.getLowestPriceProduct()).isEqualTo(lowestProduct);
            softly.assertThat(products.getHighestPriceProduct()).isEqualTo(highestProduct);
        });
    }
}