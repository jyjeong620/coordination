package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotFoundProductException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class LowestPriceProductsFactory {

    private LowestPriceProductsFactory() {
    }

    public static LowestPriceProducts create(List<Product> products) {
        Map<Category, List<Product>> productsByCategory = groupProductsByCategory(products);
        List<Product> lowestPriceProducts = findLowestPriceProducts(productsByCategory);

        BigDecimal totalPrice = calculateTotalPrice(lowestPriceProducts);

        return new LowestPriceProducts(totalPrice, lowestPriceProducts);
    }

    private static Map<Category, List<Product>> groupProductsByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    private static List<Product> findLowestPriceProducts(Map<Category, List<Product>> groupByCategory) {
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


    private static BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
