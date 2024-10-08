package com.musinsa.coordination.brand.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import com.musinsa.coordination.brand.presentation.request.BrandCreateRequest;
import com.musinsa.coordination.brand.presentation.request.BrandUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BrandRepository brandRepository;

    @DisplayName("브랜드 조회")
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