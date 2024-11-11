package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.exception.NotFoundProductException;
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
                .orElseThrow(NotFoundProductException::new);
    }

    private static Product getLowestPriceProduct(List<Product> products) {
        return products.stream()
                .min(Comparator.comparing(Product::getPrice).thenComparing(Product::getId, Comparator.reverseOrder()))
                .orElseThrow(NotFoundProductException::new);
    }
}
