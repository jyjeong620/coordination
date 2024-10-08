package com.musinsa.coordination.mock;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeBrandRepository implements BrandRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Brand> brandMap = new HashMap<>();

    @Override
    public Brand save(Brand brand) {
        ReflectionTestUtils.setField(brand, "id", idGenerator.incrementAndGet());
        brandMap.put(brand.getId(), brand);
        return brand;
    }

    @Override
    public Optional<Brand> findById(Long brandId) {
        return Optional.ofNullable(brandMap.get(brandId));
    }

    @Override
    public List<Brand> findAll() {
        return List.copyOf(brandMap.values());
    }

    @Override
    public boolean existsByName(String name) {
        return brandMap.values().stream()
                .anyMatch(brand -> brand.getName().equals(name));
    }
}
