package com.musinsa.coordination.category.presentation;

import com.musinsa.coordination.category.application.CategoryService;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.presentation.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoriesResponse> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(CategoriesResponse.from(categories));
    }
}
