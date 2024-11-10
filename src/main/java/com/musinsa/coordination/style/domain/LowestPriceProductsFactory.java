package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.Products;
import com.musinsa.coordination.product.exception.NotFoundProductException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class LowestPriceProductsFactory {

    private LowestPriceProductsFactory() {
    }

    public static Products create(List<Product> products) {
        Map<Category, List<Product>> productsByCategory = groupProductsByCategory(products);
        List<Product> lowestPriceProducts = getLowestPriceProducts(productsByCategory);

        return Products.from(lowestPriceProducts);
    }

    private static Map<Category, List<Product>> groupProductsByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    private static List<Product> getLowestPriceProducts(Map<Category, List<Product>> groupByCategory) {
        return groupByCategory.values()
                .stream()
                .map(LowestPriceProductsFactory::getLowestPriceProduct)
                .toList();
    }

    private static Product getLowestPriceProduct(List<Product> products) {
        return products.stream()
                .min(Comparator.comparing(Product::getPrice).thenComparing(Product::getId, Comparator.reverseOrder()))
                .orElseThrow(NotFoundProductException::new);
    }
}
