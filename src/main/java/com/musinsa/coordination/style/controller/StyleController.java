package com.musinsa.coordination.style.controller;

import com.musinsa.coordination.style.controller.response.LowestAndHighestPriceProductsResponse;
import com.musinsa.coordination.style.controller.response.LowestPriceBrandResponse;
import com.musinsa.coordination.style.controller.response.LowestPriceProductsResponse;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProducts;
import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Products;
import com.musinsa.coordination.style.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/styles")
@RestController
public class StyleController {

    private final StyleService styleService;

    @GetMapping("/categories/lowest-price-products")
    public ResponseEntity<LowestPriceProductsResponse> findLowestPrices() {
        Products lowestPriceProducts = styleService.getLowestPriceProductsByCategory();
        return ResponseEntity.ok(LowestPriceProductsResponse.from(lowestPriceProducts));
    }

    @GetMapping("/lowest-priced-brands")
    public ResponseEntity<LowestPriceBrandResponse> findLowestPriceBrand() {
        BrandProducts lowestPriceBrand = styleService.getLowestPriceBrand();
        return ResponseEntity.ok(LowestPriceBrandResponse.from(lowestPriceBrand));
    }

    @GetMapping("/categories/{categoryId}/lowest-highest-prices")
    public ResponseEntity<LowestAndHighestPriceProductsResponse> findLowestHighestPrices(@PathVariable Long categoryId) {
        LowestAndHighestPriceProducts products = styleService.getLowestAndHighestPriceProductsBy(categoryId);
        return ResponseEntity.ok(LowestAndHighestPriceProductsResponse.from(products));
    }
}
