package com.musinsa.coordination.brand.domain;

import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotFoundProductException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LowestPriceBrandFactory {

    private LowestPriceBrandFactory() {
    }

    public static LowestPriceBrand create(List<Product> products) {
        Map<Brand, List<Product>> productsByBrand = groupingProductByBrand(products);

        return productsByBrand.entrySet().stream()
                .map(LowestPriceBrandFactory::createLowestPriceBrand)
                .min(Comparator.comparing(LowestPriceBrand::getTotalPrice))
                .orElseThrow(NotFoundProductException::new);
    }

    private static LowestPriceBrand createLowestPriceBrand(Map.Entry<Brand, List<Product>> entry) {
        Brand brand = entry.getKey();
        List<Product> products = entry.getValue();
        BigDecimal reduce = calculateTotalPrice(products);

        return new LowestPriceBrand(brand, products, reduce);
    }

    private static BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Map<Brand, List<Product>> groupingProductByBrand(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getBrand));
    }
}
