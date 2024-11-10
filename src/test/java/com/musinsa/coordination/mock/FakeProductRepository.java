package com.musinsa.coordination.mock;

import com.musinsa.coordination.brand.domain.Brand;
import com.musinsa.coordination.category.domain.Category;
import com.musinsa.coordination.product.domain.Product;
import com.musinsa.coordination.product.domain.ProductRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.test.util.ReflectionTestUtils;

public class FakeProductRepository implements ProductRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Product save(Product product) {
        ReflectionTestUtils.setField(product, "id", idGenerator.incrementAndGet());
        productList.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .findAny();
    }

    @Override
    public List<Product> findAllLowestPriceProducts() {
        Map<String, List<Product>> groupedProducts = groupByCategoryIdAndBrandId();

        List<Product> result = new ArrayList<>();

        // 중복된 카테고리와 브랜드가 있는 경우
        groupedProducts.values()
            .forEach(productList -> {
            if (productList.size() > 1) {
                // 중복된 경우, 가격이 가장 낮은 상품을 찾아 추가합니다.
                productList.stream()
                    .min(Comparator.comparing(Product::getPrice)).ifPresent(result::add);
            } else {
                // 중복이 없을 경우, 해당 상품을 추가합니다.
                result.add(productList.getFirst());
            }
        });

        return result;

    }

    private Map<String, List<Product>> groupByCategoryIdAndBrandId() {
        Map<String, List<Product>> groupedProducts = new HashMap<>();

        // 카테고리와 브랜드를 기준으로 그룹화합니다.
        for (Product product : productList) {
            String key = product.getCategory().getId() + "-" + product.getBrand().getId();
            groupedProducts.computeIfAbsent(key, k -> new ArrayList<>()).add(product);
        }
        return groupedProducts;
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getCategory(), category))
                .toList();
    }

    @Override
    public List<Product> findAllByCategoryAndBrand(Category category, Brand brand) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getCategory(), category))
                .filter(product -> Objects.equals(product.getBrand(), brand))
                .toList();
    }
}
