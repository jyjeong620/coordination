package com.musinsa.coordination.brand.presentation;

import com.musinsa.coordination.brand.presentation.request.BrandCreateRequest;
import com.musinsa.coordination.brand.presentation.request.BrandUpdateRequest;
import com.musinsa.coordination.brand.presentation.response.BrandResponse;
import com.musinsa.coordination.brand.presentation.response.BrandsResponse;
import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.application.BrandService;
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
    public ResponseEntity<BrandsResponse> findAll() {
        List<Brand> brands = brandService.findAll();
        return ResponseEntity.ok(BrandsResponse.from(brands));
    }

    @PostMapping
    public ResponseEntity<BrandResponse> save(@RequestBody BrandCreateRequest request) {
        Brand brand = brandService.save(request.name());
        return ResponseEntity.ok(BrandResponse.from(brand));
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponse> update(
            @PathVariable Long brandId,
            @RequestBody BrandUpdateRequest request
    ) {
        Brand updatedBrand = brandService.update(brandId, request.updatedName());
        return ResponseEntity.ok(BrandResponse.from(updatedBrand));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<BrandResponse> delete(@PathVariable Long brandId) {
        Brand deletedBrand = brandService.delete(brandId);
        return ResponseEntity.ok(BrandResponse.from(deletedBrand));
    }
}
