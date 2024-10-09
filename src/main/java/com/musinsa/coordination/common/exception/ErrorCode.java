package com.musinsa.coordination.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 400
    INVALID_REQUEST_VALUE(400001, "요청값이 올바르지 않습니다."),
    NOT_ENOUGH_STOCK(400002, "해당 브랜드의 상품이 하나뿐이므로 삭제할 수 없습니다"),
    // 404,
    NOT_FOUND(404001, "요청한 데이터를 찾을 수 없습니다"),

    // 409,
    DUPLICATE_RESOURCE(409001, "이미 존재하는 데이터입니다"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return code / 1000;
    }
}
