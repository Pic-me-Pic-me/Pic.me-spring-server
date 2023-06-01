package com.with.picme.config.jwt;

public enum JwtTokenType {
    EMPTY_TOKEN,
    INVALID_TOKEN,
    EXPIRED_TOKEN,
    VALID_TOKEN,
    INVALID_SIGNATURE,
    UNSUPPORTED_JWT
}
