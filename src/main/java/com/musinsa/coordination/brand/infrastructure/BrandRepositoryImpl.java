package com.musinsa.coordination.brand.infrastructure;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Brand save(Brand brand) {
        return brandJpaRepository.save(brand);
    }

    @Override
    public Optional<Brand> findById(Long brandId) {
        return brandJpaRepository.findById(brandId);
    }

    @Override
    public List<Brand> findAll() {
        return brandJpaRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return brandJpaRepository.existsByName(name);
    }
}
