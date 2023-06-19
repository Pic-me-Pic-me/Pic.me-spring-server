package com.with.picme.common.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends PicmeException {

    public TokenException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }

}
