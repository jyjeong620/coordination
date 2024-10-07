package com.musinsa.coordination.brand.controller;

import com.musinsa.coordination.brand.controller.request.BrandCreateRequest;
import com.musinsa.coordination.brand.controller.request.BrandUpdateRequest;
import com.musinsa.coordination.brand.controller.response.BrandResponse;
import com.musinsa.coordination.brand.controller.response.BrandResponses;
import com.musinsa.coordination.brand.controller.response.BrandProductsResponse;
import com.musinsa.coordination.brand.controller.response.LowestPriceBrandResponse;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.LowestPriceBrand;
import com.musinsa.coordination.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/brands")
@RestController
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<BrandResponses> findAll() {
        List<Brand> brands = brandService.findAll();
        return ResponseEntity.ok(BrandResponses.from(brands));
    }

    @PostMapping
    public ResponseEntity<BrandResponse> save(@RequestBody BrandCreateRequest request) {
        Brand brand = brandService.save(request.name());
        return ResponseEntity.ok(BrandResponse.from(brand));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(
            @PathVariable(name = "id") Long brandId,
            @RequestBody BrandUpdateRequest request
    ) {
        Brand updatedBrand = brandService.update(brandId, request.updatedName());
        return ResponseEntity.ok(BrandResponse.from(updatedBrand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BrandResponse> delete(@PathVariable(name = "id") Long brandId) {
        Brand deletedBrand = brandService.delete(brandId);
        return ResponseEntity.ok(BrandResponse.from(deletedBrand));
    }

    @GetMapping("/lowest-price")
    public ResponseEntity<LowestPriceBrandResponse> findLowestPriceBrand() {
        LowestPriceBrand lowestPriceBrand = brandService.getLowestPriceBrand();
        return ResponseEntity.ok(LowestPriceBrandResponse.from(lowestPriceBrand));
    }
}
