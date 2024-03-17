package com.example.demo.exeptions;

public class ExeptionJWT extends RuntimeException {

    public ExeptionJWT(String message) {
        super(message);
    }

    public ExeptionJWT(String message, Throwable cause) {
        super(message, cause);
    }
}
