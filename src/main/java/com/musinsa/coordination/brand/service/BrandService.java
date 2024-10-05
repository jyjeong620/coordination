package com.musinsa.coordination.brand.service;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.exception.DuplicateBrandNameException;
import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import com.musinsa.coordination.brand.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findActiveAll() {
        return brandRepository.findActiveAll();
    }

    @Transactional
    public Brand save(String name) {
        validateBrandName(name);

        Brand brand = Brand.create(name);
        return brandRepository.save(brand);
    }

    @Transactional
    public Brand update(Long brandId, String updatedName) {
        validateBrandName(updatedName);

        Brand brand = findActiveBrandById(brandId);
        brand.updateName(updatedName);

        return brand;
    }

    @Transactional
    public Brand delete(Long brandId) {
        Brand brand = findActiveBrandById(brandId);

        brand.disable();
        return brand;
    }

    private Brand findActiveBrandById(Long brandId) {
        return brandRepository.findActiveById(brandId)
                .orElseThrow(() -> new NotFoundBrandException(brandId));
    }

    private void validateBrandName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new DuplicateBrandNameException(name);
        }
    }
}
