package com.musinsa.coordination.product.presentation;

import com.musinsa.coordination.product.application.ProductService;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.presentation.request.ProductCreateRequest;
import com.musinsa.coordination.product.presentation.request.ProductUpdateRequest;
import com.musinsa.coordination.product.presentation.response.ProductResponse;
import com.musinsa.coordination.product.presentation.response.ProductsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductsResponse> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(ProductsResponse.from(products));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductCreateRequest request) {
        Product product = productService.save(request.categoryId(), request.brandId(), request.price());
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request
    ) {
        Product product = productService.update(productId, request.categoryId(), request.brandId(), request.price());
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long productId) {
        Product product = productService.delete(productId);
        return ResponseEntity.ok(ProductResponse.from(product));
    }
}
