package com.with.picme.dto.auth.kakao;


import lombok.Builder;

@Builder
public record KakaoUserResponseDto(
        Long id,
        KakaoAccount kakao_account
) {
    public static KakaoUserResponseDto of(Long id, KakaoAccount kakaoAccount) {
        return KakaoUserResponseDto
                .builder()
                .id(id)
                .kakao_account(kakaoAccount)
                .build();
    }
}
