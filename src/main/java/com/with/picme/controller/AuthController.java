package com.with.picme.controller;

import com.with.picme.common.ApiResponse;
import com.with.picme.dto.auth.AuthSignUpRequestDto;
import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.with.picme.common.message.ResponseMessage.SUCCESS_SIGN_UP;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/auth")
    public ResponseEntity createUser(@RequestBody AuthSignUpRequestDto request) {
        AuthSignUpResponseDto response = authService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGN_UP.getMessage(), response));
    }
}
