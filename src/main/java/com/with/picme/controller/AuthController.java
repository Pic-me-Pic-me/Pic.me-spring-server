package com.with.picme.controller;

import com.with.picme.common.ApiResponse;
import com.with.picme.dto.auth.*;
import com.with.picme.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import static com.with.picme.common.message.ResponseMessage.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid AuthSignUpRequestDto request) {
        AuthSignUpResponseDto response = authService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGN_UP.getMessage(), response));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signInUser(@RequestBody AuthSignInRequestDto request) {
        AuthSignInResponseDto response = authService.signInUser(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGN_IN.getMessage(), response));
    }

    @PostMapping("/kakao/signin")
    public ResponseEntity<ApiResponse> loginSocialUser(@Valid @RequestBody AuthSocialSignInRequestDto request){
        AuthSignUpResponseDto response = authService.loginSocialUser(request);
        return ResponseEntity.ok(ApiResponse.success(SOCIAL_SIGNIN_SUCCESS.getMessage(), response));
    }

}
