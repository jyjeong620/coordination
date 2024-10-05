package com.musinsa.coordination.brand.exception;

import com.musinsa.coordination.common.exception.BusinessException;

public class DuplicateBrandNameException extends BusinessException {

    public DuplicateBrandNameException(String brandName) {
        super("이미 존재하는 브랜드 이름입니다. brandName=" + brandName);
    }
}
