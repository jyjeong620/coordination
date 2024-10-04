package com.musinsa.coordination.category.repository;

import com.musinsa.coordination.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
