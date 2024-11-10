package com.musinsa.coordination.brand.application;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import com.musinsa.coordination.brand.exception.DuplicateBrandNameException;
import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
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

        Brand brand = getBrand(brandId);
        brand.updateName(updatedName);

        return brand;
    }

    @Transactional
    public Brand delete(Long brandId) {
        Brand brand = getBrand(brandId);

        brand.disable();
        return brand;
    }

    private Brand getBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundBrandException(brandId));
    }

    private void validateBrandName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new DuplicateBrandNameException(name);
        }
    }
}
