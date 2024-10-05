package com.musinsa.coordination.product.controller.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(Long categoryId, Long brandId, BigDecimal price) {
}
