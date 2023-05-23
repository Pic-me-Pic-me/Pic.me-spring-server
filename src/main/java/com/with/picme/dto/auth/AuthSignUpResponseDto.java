package com.with.picme.dto.auth;

public record AuthSignUpResponseDto(
        Long id,
        String userName,
        String accessToken
) {
}
