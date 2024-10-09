package com.musinsa.coordination.product.exception;

import com.musinsa.coordination.common.exception.BusinessException;
import com.musinsa.coordination.common.exception.ErrorCode;

public class NotEnoughStockException extends BusinessException {

    public NotEnoughStockException(Long productId) {
        super(ErrorCode.NOT_ENOUGH_STOCK, "해당 브랜드의 상품이 하나뿐이므로 삭제할 수 없습니다 productId = " + productId);
    }
}
