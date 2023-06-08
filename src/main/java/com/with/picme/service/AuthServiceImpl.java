package com.with.picme.service;

import com.with.picme.config.SaltEncrypt;
import com.with.picme.config.jwt.JwtTokenProvider;
import com.with.picme.config.jwt.UserAuthentication;
import com.with.picme.dto.auth.AuthSignInRequestDto;
import com.with.picme.dto.auth.AuthSignInResponseDto;
import com.with.picme.dto.auth.AuthSignUpRequestDto;
import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.entity.User;
import com.with.picme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.with.picme.common.message.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SaltEncrypt saltEncrypt;
    private final JwtTokenProvider tokenProvider;

    @Override
    public AuthSignUpResponseDto createUser(AuthSignUpRequestDto request) {
        if (validateEmail(request.email()))
            throw new IllegalArgumentException(EXIST_EMAIL.getMessage());
        if (validateUserName(request.username()))
            throw new IllegalArgumentException(EXIST_USERNAME.getMessage());

        String encryptedPassword = saltEncrypt.createPasswordWithSalt(request.password());
        User user = userRepository.save(request.toEntity(encryptedPassword));

        Authentication authentication = new UserAuthentication(user.getId(), null, null);
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        user.updateRefreshToken(refreshToken);
        return AuthSignUpResponseDto.of(user, accessToken);
    }

    private boolean validateEmail(String email) {
        if (userRepository.existsUserByEmail(email))
            return true;
        return false;
    }

    private boolean validateUserName(String userName) {
        if (userRepository.existsUserByName(userName))
            return true;
        return false;
    }

    private void validateExpressions(String email, String password) {
        if (!validateEmailExpression(email) || !validatePasswordExpression(password))
            throw new IllegalArgumentException(BAD_REQUEST.getMessage());
    }

    public AuthSignInResponseDto signInUser(AuthSignInRequestDto request) {
        validateExpressions(request.email(), request.password());
        if (!validateEmail(request.email()))
            throw new EntityNotFoundException(INVALID_EMAIL.getMessage());
        User user = checkPassword(request.email(), request.password());
        Authentication authentication = new UserAuthentication(user.getId(), null, null);
        String accessToken = tokenProvider.generateAccessToken(authentication);
        return AuthSignInResponseDto.of(user, accessToken);
    }

    private boolean validateEmailExpression(String email) {
        return email.contains("@");
    }

    private boolean validatePasswordExpression(String password) {
        return password.length() >= 10;
    }

    private User checkPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (saltEncrypt.isMatch(password, user.getPassword()))
            return user;
        else
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
    }
}
