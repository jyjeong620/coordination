package com.musinsa.coordination.product.repository;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand WHERE p.price = " +
            "(SELECT MIN(p2.price) FROM Product p2 WHERE p2.brand = p.brand AND p2.category = p.category)")
    List<Product> findAllLowestPriceProducts();

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand WHERE p.category = :category")
    List<Product> findAllByCategory(Category category);
}
