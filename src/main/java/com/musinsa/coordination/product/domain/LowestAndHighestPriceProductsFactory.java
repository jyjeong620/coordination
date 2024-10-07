package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.category.domain.Category;

import java.util.Comparator;
import java.util.List;

public class LowestAndHighestPriceProductsFactory {
    private LowestAndHighestPriceProductsFactory() {
    }

    public static LowestAndHighestPriceProducts create(Category category, List<Product> products) {
        Product lowestPriceProduct = getLowestPriceProduct(products);
        Product highestPriceProduct = getHighestPriceProduct(products);

        return new LowestAndHighestPriceProducts(category, lowestPriceProduct, highestPriceProduct);
    }

    private static Product getHighestPriceProduct(List<Product> products) {
        return products.stream()
                .max(Comparator.comparing(Product::getPrice).thenComparing(Product::getId, Comparator.reverseOrder()))
                .orElseThrow(() -> new IllegalArgumentException("products is empty")); //TODO 에러처리 필요
    }

    private static Product getLowestPriceProduct(List<Product> products) {
        return products.stream()
                .min(Comparator.comparing(Product::getPrice).thenComparing(Product::getId, Comparator.reverseOrder()))
                .orElseThrow(() -> new IllegalArgumentException("products is empty"));
    }
}
