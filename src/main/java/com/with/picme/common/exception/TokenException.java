package com.with.picme.common.exception;

public class TokenException extends RuntimeException {
    private String message;

    public TokenException(String message) {
        this.message = message;
    }
}
