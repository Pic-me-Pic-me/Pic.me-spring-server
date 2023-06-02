package com.with.picme.service;

import com.with.picme.dto.auth.AuthSignUpRequestDto;
import com.with.picme.dto.auth.AuthSignUpResponseDto;

public interface AuthService {

    AuthSignUpResponseDto createUser(AuthSignUpRequestDto request);
}
