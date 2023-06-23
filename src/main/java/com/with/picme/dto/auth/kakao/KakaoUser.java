package com.with.picme.dto.auth.kakao;

import com.with.picme.config.KakaoAuth;
import lombok.Builder;

@Builder
public record KakaoUser(
        Long userId,
        String email,
        String providerType
) {
    public static KakaoUser of(Long userId, String email){
        return KakaoUser
                .builder()
                .userId(userId)
                .email(email)
                .providerType("kakao")
                .build();
    }
}
