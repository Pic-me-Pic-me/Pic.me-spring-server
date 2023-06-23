package com.with.picme.service;

import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.dto.user.UserInfoGetResponseDto;
import com.with.picme.entity.User;

public interface UserService {
    UserInfoGetResponseDto getUserInfo(Long userId);
}
