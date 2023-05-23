package com.with.picme.dto.auth;

import com.with.picme.entity.User;
import lombok.Builder;

@Builder
public record AuthSignUpRequestDto(
        String email,
        String password,
        String username
) {
    public User toEntity(String password){
        return User
                .builder()
                .email(this.email)
                .name(this.username)
                .password(password)
                .build();
    }
}
