package com.musinsa.coordination.product.presentation.request;

import java.math.BigDecimal;

public record ProductCreateRequest(Long categoryId, Long brandId, BigDecimal price) {

}
