package com.musinsa.coordination.product.service;

import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import com.musinsa.coordination.brand.repository.BrandRepository;
import com.musinsa.coordination.category.exception.NotFoundCategoryException;
import com.musinsa.coordination.category.repository.CategoryRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public Product save(Long categoryId, Long brandId, BigDecimal price) {
        validateCategoryAndBrand(categoryId, brandId);

        Product product = Product.create(categoryId, brandId, price);
        return productRepository.save(product);
    }

    private void validateCategoryAndBrand(Long categoryId, Long brandId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundCategoryException(categoryId);
        }
        if (!brandRepository.existsById(brandId)) {
            throw new NotFoundBrandException(brandId);
        }

    }
}
