package com.with.picme.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class PicmeException extends RuntimeException{
    private final HttpStatus statusCode;
    public PicmeException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
