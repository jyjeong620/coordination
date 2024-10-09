package com.musinsa.coordination.product.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.infrastructure.BrandJpaRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.infrastructure.CategoryJpaRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.infrastructure.ProductJpaRepository;
import com.musinsa.coordination.product.presentation.request.ProductCreateRequest;
import com.musinsa.coordination.product.presentation.request.ProductUpdateRequest;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
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
class ProductControllerTest {

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

    @DisplayName("상품 생성")
    @Test
    void save() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest(1L, 1L, BigDecimal.valueOf(10000));
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isOk());
    }

    @DisplayName("상품 생성 실패 - categoryId가 없는 경우")
    @Test
    void save_fail_1() throws Exception {
        // given
        Long notExistCategoryId = 100L;
        ProductCreateRequest request = new ProductCreateRequest(notExistCategoryId, 1L,
            BigDecimal.valueOf(10000));
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isNotFound());
    }

    @DisplayName("상품 생성 실패 - brandId가 없는 경우")
    @Test
    void save_fail_2() throws Exception {
        // given
        Long notExistBrandId = 100L;
        ProductCreateRequest request = new ProductCreateRequest(1L, notExistBrandId,
            BigDecimal.valueOf(10000));
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isNotFound());
    }

    @DisplayName("상품 생성 실패 - 가격이 0원인 경우")
    @Test
    void save_fail_3() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest(1L, 1L, BigDecimal.ZERO);
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("상품 수정")
    @Test
    void update() throws Exception {
        // given
        Product product = saveProduct();
        ProductUpdateRequest request = new ProductUpdateRequest(1L, 1L, BigDecimal.valueOf(99999));
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(put("/api/products/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(99999));
    }

    @DisplayName("상품 수정 실패 - 상품이 존재하지 않는 경우")
    @Test
    void update_fail_1() throws Exception {
        // given
        long notExistProductId = 1000L;
        ProductUpdateRequest request = new ProductUpdateRequest(1L, 1L, BigDecimal.valueOf(99999));
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(put("/api/products/" + notExistProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("요청한 데이터를 찾을 수 없습니다"));
    }

    @DisplayName("상품 삭제")
    @Test
    void productDelete() throws Exception {
        // given
        Product product1 = saveProduct();
        Product product2 = saveProduct();

        // when & then
        mockMvc.perform(delete("/api/products/" + product2.getId()))
            .andExpect(status().isOk());
    }

    @DisplayName("상품 삭제 실패 - 상품이 존재하지 않는 경우")
    @Test
    void productDelete_fail() throws Exception {
        // given
        long notExistProductId = 1000L;

        // when & then
        mockMvc.perform(delete("/api/products/" + notExistProductId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("요청한 데이터를 찾을 수 없습니다"));
    }

    @DisplayName("상품 삭제 실패 - 같은 카테고리, 브랜드 상품이 한개밖에 남지 않은경우")
    @Test
    void productDelete_fail_2() throws Exception {
        // given
        Product product = saveProduct();

        // when & then
        mockMvc.perform(delete("/api/products/" + product.getId()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("해당 브랜드의 상품이 하나뿐이므로 삭제할 수 없습니다"));
    }

    Product saveProduct() {
        Brand saveBrand = brandRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("브랜드가 존재하지 않습니다."));
        Category category = categoryRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        return productRepository.save(Product.create(category, saveBrand, BigDecimal.valueOf(10000)));
    }

}