package com.musinsa.coordination.common.exception;

public class InvalidRequestValueException extends BusinessException {

    public InvalidRequestValueException(String message) {
        super(ErrorCode.INVALID_REQUEST_VALUE, message);
    }
}
