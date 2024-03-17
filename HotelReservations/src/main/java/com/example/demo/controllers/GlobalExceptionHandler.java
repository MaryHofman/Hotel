package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.ErrorResponse;
import com.example.demo.exeptions.ExeptionJWT;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExeptionJWT.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleExeptionJWT(ExeptionJWT ex) {
       ErrorResponse errorResponse = new ErrorResponse("Forbidden", ex.getMessage());
       return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
