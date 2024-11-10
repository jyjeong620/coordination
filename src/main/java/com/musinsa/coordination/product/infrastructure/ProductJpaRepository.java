package com.musinsa.coordination.product.infrastructure;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand")
    List<Product> findAll();

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand WHERE p.price = " +
            "(SELECT MIN(p2.price) FROM Product p2 WHERE p2.brand = p.brand AND p2.category = p.category)")
    List<Product> findAllLowestPriceProducts();

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand WHERE p.category = :category")
    List<Product> findAllByCategory(Category category);

    List<Product> findAllByCategoryAndBrand(Category category, Brand brand);
}
