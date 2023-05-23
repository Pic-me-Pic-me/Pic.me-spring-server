package com.with.picme.service;

import com.with.picme.dto.auth.AuthSignUpRequestDto;
import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.entity.User;
import com.with.picme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.with.picme.common.message.ErrorMessage.EXIST_EMAIL;
import static com.with.picme.common.message.ErrorMessage.EXIST_USERNAME;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Override
    public AuthSignUpResponseDto createUser(AuthSignUpRequestDto request) {
        if(validateEmail(request.email()))
            throw new IllegalArgumentException(EXIST_EMAIL.getMessage());
        if(validateUserName(request.username()))
            throw new IllegalArgumentException(EXIST_USERNAME.getMessage());

        String accessToken = "";
        User user = userRepository.save(request.toEntity());
        return AuthSignUpResponseDto.from(user, accessToken);
    }

    private boolean validateEmail(String email){
        if(userRepository.findByEmail(email))
            return true;
        return false;
    }

    private boolean validateUserName(String userName){
        if(userRepository.findByUserName(userName))
            return true;
        return false;
    }
}
