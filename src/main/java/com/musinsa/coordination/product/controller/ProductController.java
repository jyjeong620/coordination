package com.musinsa.coordination.product.controller;

import com.musinsa.coordination.product.controller.request.ProductCreateRequest;
import com.musinsa.coordination.product.controller.response.ProductResponse;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
