package com.musinsa.coordination.product.repository;

import com.musinsa.coordination.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
