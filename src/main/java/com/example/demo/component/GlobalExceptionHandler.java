package com.example.demo.component;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.exception.GroupException;
import com.example.demo.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.errorBusiness(e.getExceptionEnum()));
    }

    @ExceptionHandler(value = GroupException.class)
    public ResponseEntity<?> handleGroupException(GroupException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.errorBusiness(e.getExceptionEnum()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
        StringBuilder errMsg = new StringBuilder();

        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                // GTB: - 循环中的字符串拼接推荐使用StringBuilder
                errMsg.append(fieldError.getDefaultMessage()).append(",");
            }
        }

        if (errMsg.length() > 0) {
            errMsg = new StringBuilder(errMsg.substring(0, errMsg.length() - 1));
        }

        ExceptionEnum.METHOD_ARGU_INVALID.setMessage(errMsg.toString());
        return ResponseEntity.badRequest().body(Result.errorBusiness(ExceptionEnum.METHOD_ARGU_INVALID));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String message = "";
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            message = constraintViolation.getMessage();
        }
        return ResponseEntity.badRequest().body(Result.errorSystem(message));
    }
}
