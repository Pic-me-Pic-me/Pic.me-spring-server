package com.with.picme.config.kakao;


import com.with.picme.dto.auth.kakao.KakaoUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoAuth", url = "https://kapi.kakao.com")
public interface KakaoAuth {

    @GetMapping("/v2/user/me")
    KakaoUserResponseDto getProfileInfo(@RequestHeader("Authorization") String accessToken);
}
