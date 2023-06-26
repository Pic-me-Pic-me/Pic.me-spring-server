package com.with.picme.service;

import com.with.picme.common.message.ErrorMessage;
import com.with.picme.config.SaltEncrypt;
import com.with.picme.config.jwt.JwtTokenProvider;
import com.with.picme.config.jwt.UserAuthentication;
import com.with.picme.dto.auth.*;
import com.with.picme.dto.auth.kakao.KakaoUser;
import com.with.picme.entity.AuthenticationProvider;
import com.with.picme.entity.User;
import com.with.picme.repository.AuthenticationProviderRepository;
import com.with.picme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;
import java.util.Optional;

import static com.with.picme.common.message.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SaltEncrypt saltEncrypt;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationProviderRepository authenticationProviderRepository;

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
    public AuthSignUpResponseDto loginSocialUser(AuthSocialSignInRequestDto request){
        User user = findByKey(KakaoUser.of(request.uid(), request.socialType()));
        if (Objects.isNull(user))
            throw new EntityNotFoundException(CHECK_KAKAO_USER_FAIL.getMessage());
        String accessToken = updateAccessTokenAndRefreshToken(user);
        return AuthSignUpResponseDto.of(user, accessToken);
    }

    public User findByKey(KakaoUser kakaoUser){
        Optional<AuthenticationProvider> authenticationProvider = authenticationProviderRepository
                .findByIdAndProvider(kakaoUser.userId(),
                        kakaoUser.providerType());
        // 카카오 계정은 확인, 우리 서비스에 이미 로그인함
        if(authenticationProvider.isPresent()) {
            Long userId = authenticationProvider.get().getUser().getId();
            //유저 테이블에서 찾기 , 못찾을 경우 에러 날려야 하는지 궁굼.. ->node에서는 return null로 날림
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CANT_GET_USERINFO.getMessage()));
            return user;
        }
        // 카카오 계정은 확인 됐지만, 우리 서비스에는 로그인 안됨
        return null;
    }

    private User checkPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (saltEncrypt.isMatch(password, user.getPassword()))
            return user;
        else
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
    }

    private String updateAccessTokenAndRefreshToken(User user){
        Authentication authentication = new UserAuthentication(user.getId(), null, null);
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        user.updateRefreshToken(refreshToken);
        return accessToken;
    }
}
