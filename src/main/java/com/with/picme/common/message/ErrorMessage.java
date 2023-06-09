package com.with.picme.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    BAD_REQUEST("잘못된 요청입니다"),

    /**
     * auth
     **/
    EXIST_USERNAME("이미 사용중인 닉네임입니다."),
    EXIST_EMAIL("이미 사용중인 이메일입니다."),

    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    UNSUPPORTED_TOKEN("잘못된 형식의 토큰입니다."),
    INVALID_SIGNATURE("유효하지 않은 서명입니다."),
    UNAUTHORIZED_TOKEN("인증받지 않은 토큰입니다."),
    EMPTY_TOKEN("빈 토큰입니다."),
    EMPTY_METHOD_ARGUMENT("빈 요청값이 있습니다.");

    private final String message;
}
