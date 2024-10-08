package com.musinsa.coordination.brand.domain;

import jakarta.persistence.*;
import lombok.Builder;
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
}
