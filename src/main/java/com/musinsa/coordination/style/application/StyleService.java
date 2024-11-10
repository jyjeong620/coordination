package com.musinsa.coordination.style.application;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.domain.CategoryRepository;
import com.musinsa.coordination.category.exception.NotFoundCategoryException;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.ProductRepository;
import com.musinsa.coordination.product.domain.Products;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProducts;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProductsFactory;
import com.musinsa.coordination.style.domain.LowestPriceBrandFactory;
import com.musinsa.coordination.style.domain.LowestPriceProductsFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StyleService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Products getLowestPriceProductsByCategory() {
        List<Product> products = productRepository.findAllLowestPriceProducts();

        return LowestPriceProductsFactory.create(products);
    }

    @Transactional(readOnly = true)
    public BrandProducts getLowestPriceBrand() {
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
