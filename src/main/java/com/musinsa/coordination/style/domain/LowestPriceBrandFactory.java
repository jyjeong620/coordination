package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotFoundProductException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LowestPriceBrandFactory {

    private LowestPriceBrandFactory() {
    }

    public static BrandProducts create(List<Product> products) {
        Map<Brand, List<Product>> productsByBrand = groupingProductByBrand(products);

        return productsByBrand.entrySet()
                .stream()
                .map(LowestPriceBrandFactory::createLowestPriceBrand)
                .min(Comparator.comparing(BrandProducts::getTotalPrice))
                .orElseThrow(NotFoundProductException::new);
    }

    private static BrandProducts createLowestPriceBrand(Map.Entry<Brand, List<Product>> entry) {
        Brand brand = entry.getKey();
        List<Product> products = entry.getValue();

        return BrandProducts.of(brand, products);
    }

    private static Map<Brand, List<Product>> groupingProductByBrand(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getBrand));
    }
}
