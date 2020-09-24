package com.example.demo.component;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
        String errMsg = "";

        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errMsg += fieldError.getDefaultMessage() + ",";
            }
        }

        if (errMsg.length() > 0) {
            errMsg = errMsg.substring(0, errMsg.length() - 1);
        }

        ExceptionEnum.METHOD_ARGU_INVALID.setMessage(errMsg);
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
