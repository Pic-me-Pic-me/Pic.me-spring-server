package com.with.picme.dto.auth;

import com.with.picme.entity.User;

public record AuthSignUpRequestDto(
        String email,
        String password,
        String username
) {
    public User toEntity(){
        return User
                .builder()
                .email(this.email)
                .name(this.username)
                .password(this.password)
                .build();
    }
}
