package com.musinsa.coordination.product.application;

import com.musinsa.coordination.mock.FakeBrandRepository;
import com.musinsa.coordination.mock.FakeCategoryRepository;
import com.musinsa.coordination.mock.FakeProductRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        FakeCategoryRepository fakeCategoryRepository = new FakeCategoryRepository();
        FakeBrandRepository fakeBrandRepository = new FakeBrandRepository();
        FakeProductRepository fakeProductRepository = new FakeProductRepository();
        productService = new ProductService(fakeProductRepository, fakeBrandRepository, fakeCategoryRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void save() {
        // given
        Long categoryId = 1L;
        Long brandId = 1L;
        BigDecimal price = BigDecimal.valueOf(100);

        // when
        Product product = productService.save(categoryId, brandId, price);

        // then
        assertSoftly(softly -> {
            softly.assertThat(product).isNotNull();
            softly.assertThat(product.getCategoryId()).isEqualTo(categoryId);
            softly.assertThat(product.getBrand().getId()).isEqualTo(brandId);
            softly.assertThat(product.getPrice()).isEqualTo(price);
            softly.assertThat(product.isEnable()).isTrue();
        });
    }

    @DisplayName("상품을 수정할 수 있다.")
    @Test
    void update() {
        // given
        Product product = saveProduct(1L, 1L, BigDecimal.valueOf(100));
        Long updatedCategoryId = 2L;
        Long updatedBrandId = 2L;
        BigDecimal updatedPrice = BigDecimal.valueOf(1000);

        // when
        Product updatedProduct = productService.update(product.getId(), updatedCategoryId, updatedBrandId, updatedPrice);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedProduct).isNotNull();
            softly.assertThat(updatedProduct.getCategoryId()).isEqualTo(updatedCategoryId);
            softly.assertThat(updatedProduct.getBrand().getId()).isEqualTo(updatedBrandId);
            softly.assertThat(updatedProduct.getPrice()).isEqualTo(updatedPrice);
            softly.assertThat(updatedProduct.isEnable()).isTrue();
        });
    }

    @DisplayName("상품을 삭제할 수 있다")
    @Test
    void delete() {
        // given
        Product product1 = saveProduct(1L, 1L, BigDecimal.valueOf(100));
        Product product2 = saveProduct(1L, 1L, BigDecimal.valueOf(100));

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
        Product product = saveProduct(1L, 1L, BigDecimal.valueOf(100));

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