package com.musinsa.coordination.product.domain;

import com.musinsa.coordination.category.domain.Category;
import lombok.Getter;

@Getter
public class LowestAndHighestPriceProducts {
    private final Category category;
    private final Product lowestPriceProduct;
    private final Product highestPriceProduct;

    public LowestAndHighestPriceProducts(Category category, Product lowestPriceProduct, Product highestPriceProduct) {
        this.category = category;
        this.lowestPriceProduct = lowestPriceProduct;
        this.highestPriceProduct = highestPriceProduct;
    }

    public String getCategoryName() {
        return category.getName();
    }
}
