package com.musinsa.coordination.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.from(e.getErrorCode());

        return ResponseEntity
                .status(e.getStatus())
                .body(errorResponse);
    }
}
