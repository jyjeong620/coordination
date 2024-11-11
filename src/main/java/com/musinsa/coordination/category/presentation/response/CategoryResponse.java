package com.musinsa.coordination.category.presentation.response;

import com.musinsa.coordination.category.domain.Category;

public record CategoryResponse(Long id, String name) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
