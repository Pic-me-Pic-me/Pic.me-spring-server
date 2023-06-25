package com.with.picme.service;

import com.with.picme.dto.auth.*;
import com.with.picme.dto.auth.kakao.KakaoUser;
import com.with.picme.entity.AuthenticationProvider;
import com.with.picme.entity.User;

import java.util.Optional;

public interface AuthService {

    AuthSignUpResponseDto createUser(AuthSignUpRequestDto request);
    AuthSignInResponseDto signInUser(AuthSignInRequestDto request);
    KakaoUser getUser(AuthSocialCheckRequestDto request);

    User findByKey(KakaoUser of);
}
