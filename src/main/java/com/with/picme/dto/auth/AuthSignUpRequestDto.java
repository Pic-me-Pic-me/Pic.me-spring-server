package com.with.picme.dto.auth;

import com.with.picme.entity.User;
import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Builder
public record AuthSignUpRequestDto(
        @Email(message = "잘못된 이메일 형식입니다.")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        String email,
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{10,16}",
                message = "비밀번호는 영어, 숫자를 포함한 10-16자이어야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password,
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        String username
) {
    public User toEntity(String password) {
        return User
                .builder()
                .email(this.email)
                .name(this.username)
                .password(password)
                .build();
    }
}