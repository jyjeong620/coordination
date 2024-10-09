package com.musinsa.coordination.brand.domain;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Brand save(Brand brand);

    Optional<Brand> findById(Long brandId);

    List<Brand> findAll();

    boolean existsByName(String name);
}
