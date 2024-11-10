package com.musinsa.coordination.category.exception;

import com.musinsa.coordination.common.exception.BusinessException;
import com.musinsa.coordination.common.exception.ErrorCode;

public class NotFoundCategoryException extends BusinessException {

    public NotFoundCategoryException(Long categoryId) {
        super(ErrorCode.NOT_FOUND, "카테고리를 찾을수 없습니다. categoryId : " + categoryId);
    }
}
