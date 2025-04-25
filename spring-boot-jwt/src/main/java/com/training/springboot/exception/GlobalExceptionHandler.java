package com.training.springboot.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice(basePackages = "com.training.springboot.controller")
public class GlobalExceptionHandler {
    @ExceptionHandler(MyResourceNotFoundException.class)
    public ResponseEntity<Object> handleException(MyResourceNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("message: ", "id "+ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
