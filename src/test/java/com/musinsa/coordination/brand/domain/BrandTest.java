package com.musinsa.coordination.brand.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrandTest {

    @DisplayName("브랜드 명을 이용하여 브랜드 객체를 생성할 수 있다, 브랜드 생성시 isEnable은 true로 설정된다.")
    @Test
    void create() {
        // given
        String name = "나이키";

        // when
        Brand brand = Brand.create(name);

        // then
        assertThat(brand).isNotNull();
        assertThat(brand.getName()).isEqualTo(name);
        assertThat(brand.isEnable()).isTrue();
    }

    @DisplayName("브랜드 명을 수정할 수 있다.")
    @Test
    void updateName() {
        // given
        String name = "나이키";
        Brand brand = Brand.create(name);
        String updatedName = "아디다스";

        // when
        brand.updateName(updatedName);

        // then
        assertThat(brand.getName()).isEqualTo(updatedName);
    }

    @DisplayName("브랜드를 비활성화할 수 있다.")
    @Test
    void disable() {
        // given
        String name = "나이키";
        Brand brand = Brand.create(name);

        // when
        brand.disable();

        // then
        assertThat(brand.isEnable()).isFalse();
    }
}