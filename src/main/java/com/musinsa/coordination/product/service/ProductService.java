package com.musinsa.coordination.product.service;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import com.musinsa.coordination.brand.repository.BrandRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.exception.NotFoundCategoryException;
import com.musinsa.coordination.category.repository.CategoryRepository;
import com.musinsa.coordination.common.exception.InvalidRequestValueException;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotFoundProductException;
import com.musinsa.coordination.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
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

    @Transactional
    public Product update(Long productId, Long categoryId, Long brandId, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundProductException(productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InvalidRequestValueException("잘못된 카테고리 Id 입니다. categoryId : " + categoryId));
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new InvalidRequestValueException("잘못된 브랜드 Id 입니다. brandId : " + brandId));

        product.update(category, brand, price);
        return product;
    }
}
