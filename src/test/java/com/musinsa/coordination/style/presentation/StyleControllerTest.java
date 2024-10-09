package com.musinsa.coordination.style.presentation;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.infrastructure.BrandJpaRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.infrastructure.CategoryJpaRepository;
import com.musinsa.coordination.config.DatabaseCleanup;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.infrastructure.ProductJpaRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ComponentScan(basePackages = "com.musinsa.coordination.config")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StyleControllerTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductJpaRepository productRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;
    @Autowired
    private BrandJpaRepository brandRepository;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("카테고리 별로 최저가격인 브랜드와 가격과 총액을 조회한다.")
    @Test
    void getLowestPriceProductsByCategory() throws Exception {
        // given
        Brand brand1 = brandRepository.save(Brand.create("A"));
        Brand brand2 = brandRepository.save(Brand.create("A"));
        Category category1 = categoryRepository.save(Category.create("상의"));
        Category category2 = categoryRepository.save(Category.create("하의"));
        Category category3 = categoryRepository.save(Category.create("신발"));
        Product 카테고리1의_최소가격상품 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category1.getId(), 10000);
        Product 카테고리2의_최소가격상품 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category2.getId(), 10000);
        Product 카테고리3의_최소가격상품 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category3.getId(), 10000);
        Product 카테고리1의_최대가격상품 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category1.getId(), 30000);
        Product 카테고리2의_최대가격상품 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category2.getId(), 30000);
        Product 카테고리3의_최대가격상품 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category3.getId(), 30000);

        // when & then
        mockMvc.perform(get("/api/styles/categories/lowest-priced-products")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPrice").value(30000))
            .andExpect(jsonPath("$.products[0].price").value(10000))
            .andExpect(
                jsonPath("$.products[0].category").value(카테고리1의_최소가격상품.getCategory().getName()))
            .andExpect(jsonPath("$.products[1].price").value(10000))
            .andExpect(
                jsonPath("$.products[1].category").value(카테고리2의_최소가격상품.getCategory().getName()))
            .andExpect(jsonPath("$.products[2].price").value(10000))
            .andExpect(
                jsonPath("$.products[2].category").value(카테고리3의_최소가격상품.getCategory().getName()));
    }

    @DisplayName("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액을 조회한다.")
    @Test
    void getLowestPriceBrand() throws Exception {
        // given
        Category category1 = categoryRepository.save(Category.create("상의"));
        Category category2 = categoryRepository.save(Category.create("하의"));
        Category category3 = categoryRepository.save(Category.create("신발"));

        Brand brand1 = brandRepository.save(Brand.create("A"));
        Brand brand2 = brandRepository.save(Brand.create("B"));

        Product 브랜드1_최저가격상품1 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category1.getId(), 10000);
        Product 브랜드1_최저가격상품2 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category2.getId(), 10000);
        Product 브랜드1_최저가격상품3 = saveProductWithBrandIdAndCategoryId(brand1.getId(),
            category3.getId(), 10000);
        Product 브랜드2_최대가격상품1 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category1.getId(), 30000);
        Product 브랜드2_최대가격상품2 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category2.getId(), 30000);
        Product 브랜드2_최대가격상품3 = saveProductWithBrandIdAndCategoryId(brand2.getId(),
            category3.getId(), 30000);

        // when & then
        mockMvc.perform(get("/api/styles/lowest-priced-brands")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.최저가.총액").value("30,000"))
            .andExpect(jsonPath("$.최저가.브랜드명").value(브랜드1_최저가격상품1.getBrand().getName()));
    }

    @DisplayName("요청한 카테고리의 최저가 상품과 최고가 상품을 조회한다.")
    @Test
    void getLowestAndHighestPriceProductsBy() throws Exception {
        // given
        Category category = categoryRepository.save(Category.create("상의"));
        Brand brand = brandRepository.save(Brand.create("A"));
        Product 카테고리1_최저가격상품 = saveProductWithBrandIdAndCategoryId(category.getId(), brand.getId(),
            10000);
        Product 카테고리1_최고가격상품 = saveProductWithBrandIdAndCategoryId(category.getId(), brand.getId(),
            30000);

        // when & then
        mockMvc.perform(get("/api/styles/categories/{categoryId}/lowest-highest-prices", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.카테고리").value(카테고리1_최저가격상품.getCategory().getName()))
            .andExpect(jsonPath("$.최저가.가격").value("10,000"))
            .andExpect(jsonPath("$.최고가.가격").value("30,000"));
    }

    Product saveProductWithBrandIdAndCategoryId(long brandId, long categoryId, int price) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException("브랜드가 존재하지 않습니다."));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        return productRepository.save(
            Product.create(category, brand, BigDecimal.valueOf(price)));
    }
}