package com.musinsa.coordination.common.exception;

public record ErrorResponse(int code, String message) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
