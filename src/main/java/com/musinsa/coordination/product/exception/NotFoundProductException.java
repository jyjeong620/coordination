package com.musinsa.coordination.product.exception;

import com.musinsa.coordination.common.exception.BusinessException;
import com.musinsa.coordination.common.exception.ErrorCode;

public class NotFoundProductException extends BusinessException {

    public NotFoundProductException() {
        super(ErrorCode.NOT_FOUND, "상품을 찾을수 없습니다.");
    }

    public NotFoundProductException(Long productId) {
        super(ErrorCode.NOT_FOUND, "상품을 찾을수 없습니다. productId : " + productId);
    }
}
