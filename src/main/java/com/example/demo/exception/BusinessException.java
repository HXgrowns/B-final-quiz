package com.example.demo.exception;

// GTB: - 异常粒度太粗，不能区分400和404
public class BusinessException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;

    public BusinessException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
