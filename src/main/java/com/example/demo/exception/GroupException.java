package com.example.demo.exception;


public class GroupException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;

    public GroupException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
