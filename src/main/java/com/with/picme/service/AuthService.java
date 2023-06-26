package com.with.picme.service;

import com.with.picme.dto.auth.*;

public interface AuthService {

    AuthSignUpResponseDto createUser(AuthSignUpRequestDto request);
    AuthSignInResponseDto signInUser(AuthSignInRequestDto request);

    AuthSignUpResponseDto loginSocialUser(AuthSocialSignInRequestDto reqeust);
}
