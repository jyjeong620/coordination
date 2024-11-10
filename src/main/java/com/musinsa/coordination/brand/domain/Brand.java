package com.musinsa.coordination.brand.domain;

import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@SQLRestriction("is_enable = true")
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean isEnable;

    protected Brand() {
    }

    private Brand(String name, boolean isEnable) {
        validate(name);
        this.name = name;
        this.isEnable = isEnable;
    }

    public static Brand create(String name) {
        return new Brand(name, true);
    }

    public void updateName(String updatedName) {
        this.name = updatedName;
    }

    public void disable() {
        this.isEnable = false;
    }

    private void validate(String name) {
        if(name == null || name.isBlank()) {
            throw new InvalidRequestValueException("브랜드 이름은 필수입니다.");
        }
    }
}
