package com.musinsa.coordination.brand.exception;

import com.musinsa.coordination.common.exception.BusinessException;
import com.musinsa.coordination.common.exception.ErrorCode;

public class NotFoundBrandException extends BusinessException {

    public NotFoundBrandException(Long brandId) {
        super(ErrorCode.NOT_FOUND, "브랜드를 찾을수 없습니다. brandId : " + brandId);
    }
}
