package com.musinsa.coordination.product.infrastructure;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId);
    }

    @Cacheable(value = "lowestPriceProducts")
    @Override
    public List<Product> findAllLowestPriceProducts() {
        return productJpaRepository.findAllLowestPriceProducts();
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return productJpaRepository.findAllByCategory(category);
    }

    @Override
    public List<Product> findAllByCategoryAndBrand(Category category, Brand brand) {
        return productJpaRepository.findAllByCategoryAndBrand(category, brand);
    }
}
