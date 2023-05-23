package com.with.picme.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    BAD_REQUEST("잘못된 요청입니다"),
    
    /** auth **/
    EXIST_USERNAME("이미 사용중인 닉네임입니다."),
    EXIST_EMAIL("이미 사용중인 이메일입니다.");

    private final String message;
}
