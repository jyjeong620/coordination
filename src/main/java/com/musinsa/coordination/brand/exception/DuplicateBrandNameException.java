package com.musinsa.coordination.brand.exception;

import com.musinsa.coordination.common.exception.BusinessException;
import com.musinsa.coordination.common.exception.ErrorCode;

public class DuplicateBrandNameException extends BusinessException {

    public DuplicateBrandNameException(String brandName) {
        super(ErrorCode.DUPLICATE_RESOURCE, "중복된 브랜드명입니다. brandName : " + brandName);
    }
}
