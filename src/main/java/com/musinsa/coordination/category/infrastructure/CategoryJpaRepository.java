package com.musinsa.coordination.category.infrastructure;

import com.musinsa.coordination.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
