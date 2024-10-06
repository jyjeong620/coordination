package com.musinsa.coordination.category.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@SQLRestriction("is_enable = true")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isEnable;

    protected Category() {
    }

    private Category(String name, boolean isEnable) {
        this.name = name;
        this.isEnable = isEnable;
    }

    public static Category create(String name) {
        return new Category(name, true);
    }
}

