package com.musinsa.coordination.style.presentation;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.infrastructure.BrandJpaRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.infrastructure.CategoryJpaRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.infrastructure.ProductJpaRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql(scripts = {"classpath:insert_category.sql", "classpath:insert_brand.sql"})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StyleControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductJpaRepository productRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;
    @Autowired
    private BrandJpaRepository brandRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @DisplayName("카테고리 별로 최저가격인 브랜드와 가격과 총액을 조회한다.")
    @Test
    void getLowestPriceProductsByCategory() throws Exception {
        // given

        Product 카테고리1의_최소가격상품 = saveProduct(1,10000);
        Product 카테고리2의_최소가격상품 = saveProduct(2, 10000);
        Product 카테고리3의_최소가격상품 = saveProduct(3, 10000);
        Product 카테고리1의_최대가격상품 = saveProduct(1, 30000);
        Product 카테고리2의_최대가격상품 = saveProduct(2, 30000);
        Product 카테고리3의_최대가격상품 = saveProduct(3, 30000);


        // when & then
        mockMvc.perform(get("/api/styles/lowest-price-products")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPrice").value(30000))
            .andExpect(jsonPath("$.products[0].price").value(10000))
            .andExpect(jsonPath("$.products[1].price").value(10000))
            .andExpect(jsonPath("$.products[2].price").value(10000));


    }

    Product saveProduct(long categoryId, int price) {
        Brand saveBrand = brandRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("브랜드가 존재하지 않습니다."));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        return productRepository.save(Product.create(category, saveBrand, BigDecimal.valueOf(price)));
    }
}