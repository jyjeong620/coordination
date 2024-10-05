package com.musinsa.coordination.brand.repository;

import com.musinsa.coordination.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b WHERE b.isEnable = true")
    List<Brand> findActiveAll();

    @Query("SELECT b FROM Brand b WHERE b.id = :id and b.isEnable = true")
    Optional<Brand> findActiveById(Long id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Brand b WHERE b.name = :name and b.isEnable = true")
    boolean existsByName(String name);
}
