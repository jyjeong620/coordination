package com.musinsa.coordination.category.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long categoryId);

    List<Category> findAll();
}
