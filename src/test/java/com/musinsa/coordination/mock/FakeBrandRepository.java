package com.musinsa.coordination.mock;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.test.util.ReflectionTestUtils;

public class FakeBrandRepository implements BrandRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Brand> brandMap = new HashMap<>();

    public FakeBrandRepository() {
    }

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
