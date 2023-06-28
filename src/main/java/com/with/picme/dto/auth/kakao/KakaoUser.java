package com.with.picme.dto.auth.kakao;


import com.with.picme.entity.ProviderType;
import lombok.Builder;

@Builder
public record KakaoUser(
        Long userId,
        String email,
        ProviderType providerType
) {
    public static KakaoUser of(Long userId, String email){
        return KakaoUser
                .builder()
                .userId(userId)
                .email(email)
                .providerType(ProviderType.kakao)
                .build();
    }
}
