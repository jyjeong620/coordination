package com.musinsa.coordination.brand.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

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

    @DisplayName("브랜드 명이 null이거나 빈 문자열일 경우 에러를 발생시킨다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_InvalidName(String name) {
        // when & then
        assertThatThrownBy(() -> Brand.create(name))
                .isInstanceOf(InvalidRequestValueException.class)
                .hasMessage("브랜드 이름은 필수입니다.");
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