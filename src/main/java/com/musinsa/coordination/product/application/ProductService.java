package com.musinsa.coordination.product.application;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import com.musinsa.coordination.brand.exception.NotFoundBrandException;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.domain.CategoryRepository;
import com.musinsa.coordination.category.exception.NotFoundCategoryException;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.ProductRepository;
import com.musinsa.coordination.product.exception.NotEnoughStockException;
import com.musinsa.coordination.product.exception.NotFoundProductException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product save(Long categoryId, Long brandId, BigDecimal price) {
        Category category = getCategory(categoryId);
        Brand brand = getBrand(brandId);

        Product product = Product.create(category, brand, price);
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long productId, Long categoryId, Long brandId, BigDecimal price) {
        Product product = getProduct(productId);
        Category category = getCategory(categoryId);
        Brand brand = getBrand(brandId);

        product.update(category, brand, price);
        return product;
    }

    @Transactional
    public Product delete(Long productId) {
        Product product = getProduct(productId);

        validateEnoughStock(product);

        product.disable();
        return product;
    }

    private void validateEnoughStock(Product product) {
        List<Product> products = productRepository.findAllByCategoryAndBrand(product.getCategory(), product.getBrand());
        if (products.size() == 1) {
            throw new NotEnoughStockException(product.getId());
        }
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundProductException(productId));
    }

    private Brand getBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundBrandException(brandId));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException(categoryId));
    }
}
