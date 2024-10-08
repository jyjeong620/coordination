//package com.musinsa.coordination.brand.service;
//
//import com.musinsa.coordination.brand.domain.Brand;
//import com.musinsa.coordination.brand.repository.BrandRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BrandServiceTest {
//
//    @InjectMocks
//    private BrandService brandService;
//
//    @Mock
//    private BrandRepository brandRepository;
//
//
//    @DisplayName("브랜드를 등록할 수있다.")
//    @Test
//    void save() {
//        // given
//        String name = "나이키";
//        Brand brand = Brand.create(name);
//        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
//
//        // when
//        Brand savedBrand = brandService.save(name);
//
//        // then
//        assertThat(savedBrand).isNotNull();
//        assertThat(savedBrand.getName()).isEqualTo(name);
//    }
//
//    @DisplayName("브랜드를 수정할 수 있다.")
//    @Test
//    void update() {
//        // given
//        String name = "나이키";
//        Brand brand = Brand.create(name);
//        String updatedName = "아디다스";
//        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
//        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
//
//        // when
//        Brand updatedBrand = brandService.update(1L, updatedName);
//
//        // then
//        assertThat(updatedBrand.getName()).isEqualTo(updatedName);
//    }
//}