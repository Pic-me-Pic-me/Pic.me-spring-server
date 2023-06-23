package com.with.picme.dto.auth;


import javax.validation.constraints.NotBlank;


public record AuthSocialCheckRequestDto(
        @NotBlank(message="필요한 값이 없습니다")
        String socialType,

        @NotBlank(message = "필요한 값이 없습니다.")
        String token
) {
}
