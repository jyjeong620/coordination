package com.musinsa.coordination.brand.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.infrastructure.BrandJpaRepository;
import com.musinsa.coordination.brand.presentation.request.BrandCreateRequest;
import com.musinsa.coordination.brand.presentation.request.BrandUpdateRequest;
import com.musinsa.coordination.config.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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
class BrandControllerTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BrandJpaRepository brandRepository;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("브랜드 생성")
    @Test
    void save() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("나이키");
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isOk());
    }

    @DisplayName("브랜드 생성 실패_요청값이 올바르지 않음")
    @ParameterizedTest
    @NullAndEmptySource
    void save_fail(String name) throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest(name);
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("요청값이 올바르지 않습니다."));
    }

    @DisplayName("브랜드 생성 실패_중복된 브랜드명")
    @Test
    void save_fail_duplicate() throws Exception {
        // given
        Brand brand = Brand.create("나이키");
        brandRepository.save(brand);

        BrandCreateRequest request = new BrandCreateRequest("나이키");
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("이미 존재하는 데이터입니다"));
    }

    @DisplayName("브랜드 수정")
    @Test
    void update() throws Exception {
        // given
        Brand brand = Brand.create("나이키");
        Brand saveBrand = brandRepository.save(brand);

        BrandUpdateRequest request = new BrandUpdateRequest("아디다스");
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(put("/api/brands/" + saveBrand.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
            .andExpect(status().isOk());
    }

    @DisplayName("브랜드 삭제")
    @Test
    void brandDelete() throws Exception {
        // given
        Brand brand = Brand.create("나이키");
        Brand saveBrand = brandRepository.save(brand);

        // when & then
        mockMvc.perform(delete("/api/brands/" + saveBrand.getId()))
            .andExpect(status().isOk());
    }
}