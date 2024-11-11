package com.musinsa.coordination.mock;

import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.domain.CategoryRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.test.util.ReflectionTestUtils;

public class FakeCategoryRepository implements CategoryRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Category> categoryMap = new HashMap<>();

    public FakeCategoryRepository() {
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(categoryMap.get(categoryId));
    }

    @Override
    public List<Category> findAll() {
        return List.copyOf(categoryMap.values());
    }

    public Category save(Category category) {
        ReflectionTestUtils.setField(category, "id", idGenerator.incrementAndGet());
        categoryMap.put(category.getId(), category);
        return category;
    }
}
