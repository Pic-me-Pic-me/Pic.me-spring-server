package com.with.picme.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    /*
    auth
     */
    SUCCESS_SIGN_UP("회원가입 성공"),
    SUCCESS_SIGN_IN("로그인 성공"),

    /*
    vote
     */
    SUCCESS_CLOSE_VOTE("투표 종료 성공"),

    /*
    user
     */
    GET_USER_INFO("유저 정보 갖고오기 성공");

    private final String message;
    }
