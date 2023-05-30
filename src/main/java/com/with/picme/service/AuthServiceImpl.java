package com.with.picme.service;

import com.with.picme.config.SaltEncrypt;
import com.with.picme.config.jwt.JwtTokenProvider;
import com.with.picme.config.jwt.UserAuthentication;
import com.with.picme.dto.auth.AuthSignUpRequestDto;
import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.entity.User;
import com.with.picme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.with.picme.common.message.ErrorMessage.EXIST_EMAIL;
import static com.with.picme.common.message.ErrorMessage.EXIST_USERNAME;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SaltEncrypt saltEncrypt;
    private final JwtTokenProvider tokenProvider;
    @Override
    public AuthSignUpResponseDto createUser(AuthSignUpRequestDto request) {
        try {
            if (validateEmail(request.email()))
                throw new IllegalArgumentException(EXIST_EMAIL.getMessage());
            if (validateUserName(request.username()))
                throw new IllegalArgumentException(EXIST_USERNAME.getMessage());

            String encryptedPassword = saltEncrypt.createPasswordWithSalt(request.password());
            System.out.println("request.email :" + request.email() + ",. request.password: " + encryptedPassword + ", nickname: " + request.username());
            User user = userRepository.save(request.toEntity(encryptedPassword));

            Authentication authentication = new UserAuthentication(user.getId(), null, null);
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);
            user.updateRefreshToken(refreshToken);
            return AuthSignUpResponseDto.from(user, accessToken);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        }

    private boolean validateEmail(String email){
        if(userRepository.existsUserByEmail(email))
            return true;
        return false;
    }

    private boolean validateUserName(String userName){
        if(userRepository.existsUserByName(userName))
            return true;
        return false;
    }
}
