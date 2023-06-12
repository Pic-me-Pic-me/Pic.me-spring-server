package com.with.picme.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record AuthSignInRequestDto(
        @Email(message = "잘못된 이메일 형식입니다.")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        String email,

        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{10,16}",
                message = "비밀번호는 영어, 숫자를 포함한 10-16자이어야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {

}
