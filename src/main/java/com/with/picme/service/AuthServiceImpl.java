package com.with.picme.service;

import com.with.picme.common.SocialType;
import com.with.picme.config.KakaoAuth;
import com.with.picme.config.SaltEncrypt;
import com.with.picme.config.jwt.JwtTokenProvider;
import com.with.picme.config.jwt.UserAuthentication;
import com.with.picme.dto.auth.*;
import com.with.picme.dto.auth.kakao.KakaoAccount;
import com.with.picme.dto.auth.kakao.KakaoUser;
import com.with.picme.dto.auth.kakao.KakaoUserResponseDto;
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
    private final KakaoAuth kakaoAuth;

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

    public AuthSignInResponseDto signInUser(AuthSignInRequestDto request) {
        if (!validateEmail(request.email()))
            throw new EntityNotFoundException(INVALID_EMAIL.getMessage());
        User user = checkPassword(request.email(), request.password());
        Authentication authentication = new UserAuthentication(user.getId(), null, null);
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        user.updateRefreshToken(refreshToken);
        return AuthSignInResponseDto.of(user, accessToken);
    }

    @Override
    public KakaoUser getUser(AuthSocialCheckRequestDto request) {
        if (!request.socialType().equals(SocialType.kakao)){
            throw new IllegalArgumentException(NO_SOCIAL_TYPE.getMessage());
        }
        //카카오 계정 확인
        KakaoUserResponseDto user = kakaoAuth.getProfileInfo(request.token());
        checkSocialUser(user);
        // 이메일 확인 -> 이메일 없는 경우 ""으로 대체
        KakaoUserResponseDto kakaoUser = checkSocialUserHaveEmail(user);
        return KakaoUser.of(kakaoUser.id(), kakaoUser.kakao_account().email());
    }

    private User checkPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (saltEncrypt.isMatch(password, user.getPassword()))
            return user;
        else
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
    }

    private boolean checkSocialUser(KakaoUserResponseDto kakaoUser){
        if (kakaoUser.id() != null){
            throw new IllegalArgumentException(NO_SOCIAL_USER.getMessage());
        }
        return true;
    }

    private KakaoUserResponseDto checkSocialUserHaveEmail(KakaoUserResponseDto kakaoUserResponseDto){
        if (kakaoUserResponseDto.kakao_account().email() == null){
            return KakaoUserResponseDto.of(kakaoUserResponseDto.id(), KakaoAccount.of(""));
        }
        return kakaoUserResponseDto;
    }
}
