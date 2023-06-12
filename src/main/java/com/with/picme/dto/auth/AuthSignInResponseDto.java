package com.with.picme.dto.auth;

import com.with.picme.entity.User;
import lombok.Builder;

@Builder
public record AuthSignInResponseDto(
        Long id,
        String userName,
        String accessToken
) {
    public static AuthSignInResponseDto of(User user, String accessToken) {
        return AuthSignInResponseDto
                .builder()
                .id(user.getId())
                .userName(user.getName())
                .accessToken(accessToken)
                .build();
    }
}
