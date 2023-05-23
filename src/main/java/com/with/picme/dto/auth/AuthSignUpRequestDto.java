package com.with.picme.dto.auth;

public record AuthSignUpRequestDto(
        String email,
        String password,
        String username
) {
}
