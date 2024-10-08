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

    public FakeBrandRepository() {
        Brand A = Brand.create("A");
        Brand B = Brand.create("B");
        Brand C = Brand.create("C");
        Brand D = Brand.create("D");
        Brand E = Brand.create("E");
        Brand F = Brand.create("F");
        Brand G = Brand.create("G");

        ReflectionTestUtils.setField(A, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(B, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(C, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(D, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(E, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(F, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(G, "id", idGenerator.incrementAndGet());

        brandMap.put(A.getId(), A);
        brandMap.put(B.getId(), B);
        brandMap.put(C.getId(), C);
        brandMap.put(D.getId(), D);
        brandMap.put(E.getId(), E);
        brandMap.put(F.getId(), F);
        brandMap.put(G.getId(), G);
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
