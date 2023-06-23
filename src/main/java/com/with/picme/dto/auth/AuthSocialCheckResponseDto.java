package com.with.picme.dto.auth;

import lombok.Builder;

@Builder
public record AuthSocialCheckResponseDto (
        Long uid,
        String email,
        boolean isUser
){
    public static AuthSocialCheckResponseDto of(Long uid, String email, boolean isUser){
        return AuthSocialCheckResponseDto
                .builder()
                .uid(uid)
                .email(email)
                .isUser(isUser)
                .build();

    }
}
