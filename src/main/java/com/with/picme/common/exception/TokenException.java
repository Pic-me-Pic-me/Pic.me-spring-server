package com.with.picme.common.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {
    private String message;
    private HttpStatus statusCode;

    public TokenException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
