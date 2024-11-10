package com.musinsa.coordination.product.application;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.mock.FakeBrandRepository;
import com.musinsa.coordination.mock.FakeCategoryRepository;
import com.musinsa.coordination.mock.FakeProductRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotEnoughStockException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private FakeProductRepository productRepository;
    private FakeCategoryRepository categoryRepository;
    private FakeBrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new FakeCategoryRepository();
        productRepository = new FakeProductRepository();
        brandRepository = new FakeBrandRepository();
        productService = new ProductService(productRepository, brandRepository, categoryRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void save() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("나이키"));
        BigDecimal price = BigDecimal.valueOf(100);

        // when
        Product product = productService.save(category.getId(), brand.getId(), price);

        // then
        assertSoftly(softly -> {
            softly.assertThat(product).isNotNull();
            softly.assertThat(product.getCategoryId()).isEqualTo(category.getId());
            softly.assertThat(product.getBrand().getId()).isEqualTo(brand.getId());
            softly.assertThat(product.getPrice()).isEqualTo(price);
            softly.assertThat(product.isEnable()).isTrue();
        });
    }

    @DisplayName("상품을 수정할 수 있다.")
    @Test
    void update() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("나이키"));
        Product product = saveProduct(category.getId(), brand.getId(), BigDecimal.valueOf(100));

        Category updatedCategory = categoryRepository.save(Category.create("하의"));
        Brand updatedBrand = brandRepository.save(Brand.create("아디다스"));
        BigDecimal updatedPrice = BigDecimal.valueOf(1000);

        // when
        Product updatedProduct = productService.update(product.getId(), updatedCategory.getId(), updatedBrand.getId(), updatedPrice);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedProduct).isNotNull();
            softly.assertThat(updatedProduct.getCategoryId()).isEqualTo(updatedCategory.getId());
            softly.assertThat(updatedProduct.getBrand().getId()).isEqualTo(updatedBrand.getId());
            softly.assertThat(updatedProduct.getPrice()).isEqualTo(updatedPrice);
            softly.assertThat(updatedProduct.isEnable()).isTrue();
        });
    }

    @DisplayName("상품을 삭제할 수 있다")
    @Test
    void delete() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("나이키"));
        Product product1 = saveProduct(category.getId(), brand.getId(), BigDecimal.valueOf(100));
        Product product2 = saveProduct(category.getId(), brand.getId(), BigDecimal.valueOf(100));

        // when
        productService.delete(product2.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(product2).isNotNull();
            softly.assertThat(product2.isEnable()).isFalse();
            softly.assertThat(product1.isEnable()).isTrue();
        });
    }

    @DisplayName("같은 브랜드, 카테고리를 가진 상품이 1개일 경우 에러를 응답한다")
    @Test
    void deleteException() {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("나이키"));
        Product product = saveProduct(category.getId(), brand.getId(), BigDecimal.valueOf(100));

        // when & then
        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> productService.delete(product.getId()))
                    .isInstanceOf(NotEnoughStockException.class)
                    .hasMessageContaining("해당 브랜드의 상품이 하나뿐이므로 삭제할 수 없습니다 productId = " + product.getId());
            softly.assertThat(product.isEnable()).isTrue();
        });
    }

    Product saveProduct(Long categoryId, Long brandId, BigDecimal price) {
        return productService.save(categoryId, brandId, price);
    }
}