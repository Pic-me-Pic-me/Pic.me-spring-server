package com.with.picme.dto.auth;

import com.with.picme.entity.User;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record AuthSignUpRequestDto(
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
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
