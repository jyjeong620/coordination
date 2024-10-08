package com.musinsa.coordination.brand.infrastructure;

import com.musinsa.coordination.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findActiveById(Long id);

    boolean existsByName(String name);
}
