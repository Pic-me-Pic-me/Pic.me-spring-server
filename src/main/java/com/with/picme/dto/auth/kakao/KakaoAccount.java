package com.with.picme.dto.auth.kakao;

import lombok.Builder;

@Builder
public record KakaoAccount(
        String email
) {
    public static KakaoAccount of(String email){
        return KakaoAccount
                .builder()
                .email(email)
                .build();
    }
}