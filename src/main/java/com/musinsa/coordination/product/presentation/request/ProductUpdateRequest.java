package com.musinsa.coordination.product.presentation.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(Long categoryId, Long brandId, BigDecimal price) {

}
