package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Long productId);

    List<Product> findAllLowestPriceProducts();

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByCategoryAndBrand(Category category, Brand brand);
}
