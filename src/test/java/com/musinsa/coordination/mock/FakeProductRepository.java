package com.musinsa.coordination.mock;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.ProductRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductRepository implements ProductRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Product save(Product product) {
        ReflectionTestUtils.setField(product, "id", idGenerator.incrementAndGet());
        productList.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .findAny();
    }

    @Override
    public List<Product> findAllLowestPriceProducts() {

        return List.of();
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getCategory(), category))
                .toList();
    }

    @Override
    public List<Product> findAllByCategoryAndBrand(Category category, Brand brand) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getCategory(), category))
                .filter(product -> Objects.equals(product.getBrand(), brand))
                .toList();
    }
}
