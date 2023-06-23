package com.with.picme.service;

import com.with.picme.dto.auth.*;
import com.with.picme.dto.auth.kakao.KakaoUser;

public interface AuthService {

    AuthSignUpResponseDto createUser(AuthSignUpRequestDto request);
    AuthSignInResponseDto signInUser(AuthSignInRequestDto request);
    KakaoUser getUser(AuthSocialCheckRequestDto request);
}
