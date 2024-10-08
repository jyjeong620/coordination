package com.musinsa.coordination.style.domain;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
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
