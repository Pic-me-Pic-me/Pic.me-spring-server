package com.with.picme.config.kakao;

import com.with.picme.common.message.ErrorMessage;
import com.with.picme.dto.auth.kakao.KakaoUser;
import com.with.picme.dto.auth.kakao.KakaoUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static com.with.picme.common.message.ErrorMessage.*;

@RequiredArgsConstructor
@Component
public class KakaoAuthImpl implements KakaoAuth {
    private final KakaoAuth kakaoAuth;

    @Override
    public KakaoUserResponseDto getProfileInfo(String accessToken) {
        try {
            KakaoUserResponseDto kakaoProfile = kakaoAuth.getProfileInfo(accessToken);
            return kakaoProfile;
        } catch (Exception e) {
            throw new EntityNotFoundException(ErrorMessage.NOT_FOUND_SOCIAL_TOKEN.getMessage());
        }
    }

    public KakaoUser getKakaoUser(String accessToken){
        KakaoUserResponseDto user = this.getProfileInfo(accessToken);
        checkSocialUser(user);
        KakaoUser kakaoUser = checkSocialUserHaveEmail(user);
        return kakaoUser;
    }

    private boolean checkSocialUser(KakaoUserResponseDto kakaoUser){
        if (kakaoUser.id() == null){
            throw new IllegalArgumentException(NO_SOCIAL_USER.getMessage());
        }
        return true;
    }

    private KakaoUser checkSocialUserHaveEmail(KakaoUserResponseDto kakaoUserResponseDto){
        String email = "";
        if (kakaoUserResponseDto.kakao_account().email() != null) {
            email = kakaoUserResponseDto.kakao_account().email();
        }
        return KakaoUser.of(kakaoUserResponseDto.id(), email);
    }
}
