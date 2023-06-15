package com.with.picme.controller;

import com.with.picme.common.ApiResponse;
import com.with.picme.common.message.ErrorMessage;
import com.with.picme.common.message.ResponseMessage;
import com.with.picme.dto.user.UserInfoGetResponseDto;
import com.with.picme.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getUserInfo(Principal principal) {
        Long userId = getUser(principal);
        UserInfoGetResponseDto response = userService.getUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.GET_USER_INFO.getMessage(),response));
    }

    private Long getUser(Principal principal){
        if (isNull(principal.getName()))
            throw new IllegalArgumentException(ErrorMessage.EMPTY_TOKEN.getMessage());
        return Long.valueOf(principal.getName());
    }
}
