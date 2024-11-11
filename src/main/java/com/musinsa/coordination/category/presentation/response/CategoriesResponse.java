package com.musinsa.coordination.category.presentation.response;

import com.musinsa.coordination.category.domain.Category;

import java.util.List;

public record CategoriesResponse(List<CategoryResponse> categories) {

    public static CategoriesResponse from(List<Category> categories) {
        List<CategoryResponse> response = categories.stream()
                .map(CategoryResponse::from)
                .toList();
        return new CategoriesResponse(response);
    }
}
