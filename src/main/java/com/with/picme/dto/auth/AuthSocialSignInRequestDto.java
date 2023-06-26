package com.with.picme.dto.auth;

import javax.validation.constraints.NotBlank;

public record AuthSocialSignInRequestDto (
        Long uid,
        @NotBlank(message = "소셜타입은 필수 입력값입니다.")
        String socialType
){
}
