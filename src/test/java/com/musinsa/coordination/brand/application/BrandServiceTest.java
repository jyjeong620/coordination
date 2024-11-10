package com.musinsa.coordination.brand.application;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.exception.DuplicateBrandNameException;
import com.musinsa.coordination.mock.FakeBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrandServiceTest {

    private BrandService brandService;

    @BeforeEach
    void init() {
        FakeBrandRepository brandRepository = new FakeBrandRepository();
        brandService = new BrandService(brandRepository);
    }

    @DisplayName("브랜드를 등록할 수있다.")
    @Test
    void save() {
        // given
        String name = "나이키";

        // when
        Brand savedBrand = brandService.save(name);

        // then
        assertSoftly(softly -> {
            softly.assertThat(savedBrand).isNotNull();
            softly.assertThat(savedBrand.getName()).isEqualTo(name);
        });
    }

    @DisplayName("브랜드를 등록시 중복된 이름이 있으면 예외가 발생한다.")
    @Test
    void save_fail() {
        // given
        String name = "나이키";
        brandService.save(name);

        // when & then
        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> brandService.save(name))
                    .isInstanceOf(DuplicateBrandNameException.class);
        });
    }

    @DisplayName("브랜드를 수정할 수 있다.")
    @Test
    void update() {
        // given
        String name = "나이키";
        Brand saveBrand = brandService.save(name);

        String updatedName = "아디다스";

        // when
        Brand updatedBrand = brandService.update(saveBrand.getId(), updatedName);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedBrand).isNotNull();
            softly.assertThat(updatedBrand.getName()).isEqualTo(updatedName);
            softly.assertThat(updatedBrand.getId()).isEqualTo(saveBrand.getId());
        });
    }

    @DisplayName("브랜드를 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        String name = "나이키";
        Brand saveBrand = brandService.save(name);

        // when
        Brand deletedBrand = brandService.delete(saveBrand.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(deletedBrand).isNotNull();
            softly.assertThat(deletedBrand.getId()).isEqualTo(saveBrand.getId());
            softly.assertThat(deletedBrand.isEnable()).isFalse();
        });
    }
}