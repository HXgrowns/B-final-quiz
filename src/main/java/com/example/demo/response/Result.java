package com.example.demo.response;

import com.example.demo.exception.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Result {
    private int code;
    private String message;
    private Instant timestamp;
    private long status;

    public static Result errorBusiness(ExceptionEnum e) {
        return Result.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Instant.now())
                .timestamp(Instant.now())
                .build();
    }

    public static Result errorSystem(String message) {
        return Result.builder()
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

}
