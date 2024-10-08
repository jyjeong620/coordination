package com.musinsa.coordination.mock;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.brand.domain.BrandRepository;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.category.domain.CategoryRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeCategoryRepository implements CategoryRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Category> categoryMap = new HashMap<>();

    public FakeCategoryRepository() {
        Category 상의 = Category.create("상의");
        Category 아우터 = Category.create("아우터");
        Category 바지 = Category.create("바지");
        Category 스니커즈 = Category.create("스니커즈");
        Category 가방 = Category.create("가방");
        Category 모자 = Category.create("모자");
        Category 양말 = Category.create("양말");
        Category 액세서리 = Category.create("액세서리");

        ReflectionTestUtils.setField(상의, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(아우터, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(바지, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(스니커즈, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(가방, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(모자, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(양말, "id", idGenerator.incrementAndGet());
        ReflectionTestUtils.setField(액세서리, "id", idGenerator.incrementAndGet());

        categoryMap.put(상의.getId(), 상의);
        categoryMap.put(아우터.getId(), 아우터);
        categoryMap.put(바지.getId(), 바지);
        categoryMap.put(스니커즈.getId(), 스니커즈);
        categoryMap.put(가방.getId(), 가방);
        categoryMap.put(모자.getId(), 모자);
        categoryMap.put(양말.getId(), 양말);
        categoryMap.put(액세서리.getId(), 액세서리);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(categoryMap.get(categoryId));
    }
}
