package com.with.picme.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

     SUCCESS_SIGN_UP("회원가입 성공");

    private final String message;
}
