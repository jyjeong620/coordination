package com.musinsa.coordination.brand.service;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.exception.DuplicateBrandNameException;
import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import com.musinsa.coordination.brand.repository.BrandRepository;
import com.musinsa.coordination.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

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

        Brand brand = findById(brandId);
        brand.updateName(updatedName);

        return brand;
    }

    @Transactional
    public Brand delete(Long brandId) {
        Brand brand = findById(brandId);

        brand.disable();
        return brand;
    }

    private Brand findById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundBrandException(brandId));
    }

    private void validateBrandName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new DuplicateBrandNameException(name);
        }
    }

}
