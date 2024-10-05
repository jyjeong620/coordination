package com.musinsa.coordination.brand.exception;

import com.musinsa.coordination.common.exception.BusinessException;

public class NotFoundBrandException extends BusinessException {

    public NotFoundBrandException(Long brandId) {
        super("존재하지 않는 브랜드입니다. brandId=" + brandId);
    }
}
