package com.musinsa.coordination.product.controller;

import com.musinsa.coordination.product.controller.request.ProductCreateRequest;
import com.musinsa.coordination.product.controller.request.ProductUpdateRequest;
import com.musinsa.coordination.product.controller.response.LowestPriceProductResponse;
import com.musinsa.coordination.product.controller.response.ProductResponse;
import com.musinsa.coordination.product.domain.LowestPriceProducts;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductCreateRequest request) {
        Product product = productService.save(request.categoryId(), request.brandId(), request.price());
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest request
    ) {
        Product product = productService.update(id, request.categoryId(), request.brandId(), request.price());
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lowest-prices")
    public ResponseEntity<LowestPriceProductResponse> findLowestPrices() {
        LowestPriceProducts products = productService.getLowestPriceProductsByCategory();
        return ResponseEntity.ok(LowestPriceProductResponse.from(products));
    }
}
