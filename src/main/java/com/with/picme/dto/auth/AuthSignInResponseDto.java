package com.with.picme.dto.auth;

public record AuthSignInResponseDto(
        Long id,
        String userName,
        String accessToken
) {
}
