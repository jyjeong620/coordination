package com.musinsa.coordination.brand.infrastructure;

import com.musinsa.coordination.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);
}
