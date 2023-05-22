package com.with.picme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.with.picme.common.ApiResponse;

@RestController
public class TestController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(ApiResponse.success("Hello Picme Server!"));
    }
}