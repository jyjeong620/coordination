package com.musinsa.coordination.style.service;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.exception.NotFoundCategoryException;
import com.musinsa.coordination.category.repository.CategoryRepository;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.repository.ProductRepository;
import com.musinsa.coordination.style.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StyleService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public LowestPriceProducts getLowestPriceProductsByCategory() {
        List<Product> products = productRepository.findAll();

        return LowestPriceProductsFactory.create(products);
    }

    @Transactional(readOnly = true)
    public LowestPriceBrand getLowestPriceBrand() {
        List<Product> products = productRepository.findAllLowestPriceProducts();

        return LowestPriceBrandFactory.create(products);
    }

    @Transactional(readOnly = true)
    public LowestAndHighestPriceProducts getLowestAndHighestPriceProductsBy(Long categoryId) {
        Category category = getCategory(categoryId);
        List<Product> products = productRepository.findAllByCategory(category);

        return LowestAndHighestPriceProductsFactory.create(category, products);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException(categoryId));
    }
}
