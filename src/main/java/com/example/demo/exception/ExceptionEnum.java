package com.example.demo.exception;

public enum  ExceptionEnum {
    SUCCESS(0, "success"),
    UNKNOWN_ERROR(1, "unknown_error"),
    METHOD_ARGU_INVALID(2, ""),
    TRAINEE_NOT_FOUND(3, "trainee not found"),
    TRAINER_NOT_FOUND(4, "trainer not found"),
    GROUP_NOT_ENOUGT(5,"group not enough"),
    GROUP_NOT_FOUND(6, "group not found"),
    GROUP_NAME_EXIST(7, "group name is exist");

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
