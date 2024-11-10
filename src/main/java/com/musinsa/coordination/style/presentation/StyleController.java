package com.musinsa.coordination.style.presentation;

import com.musinsa.coordination.product.domain.BrandProducts;
import com.musinsa.coordination.product.domain.Products;
import com.musinsa.coordination.style.application.StyleService;
import com.musinsa.coordination.style.domain.LowestAndHighestPriceProducts;
import com.musinsa.coordination.style.presentation.response.LowestAndHighestPriceProductsResponse;
import com.musinsa.coordination.style.presentation.response.LowestPriceBrandResponse;
import com.musinsa.coordination.style.presentation.response.LowestPriceProductsResponse;
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

    @GetMapping("/categories/lowest-priced-products")
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
