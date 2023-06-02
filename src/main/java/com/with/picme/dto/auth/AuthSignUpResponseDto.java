package com.with.picme.dto.auth;

import com.with.picme.entity.User;
import lombok.Builder;

@Builder
public record AuthSignUpResponseDto(
        Long id,
        String userName,
        String accessToken
) {
    public static AuthSignUpResponseDto of(User user, String accessToken) {
        return AuthSignUpResponseDto
                .builder()
                .id(user.getId())
                .accessToken(accessToken)
                .userName(user.getName())
                .build();
    }
}
