package com.example.demo.api.user_account.controller;

import com.example.demo.api.common.Result;
import com.example.demo.api.user_account.dto.UserAccountRequest;
import com.example.demo.api.user_account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("signup")
    public ResponseEntity<Result> signup(UserAccountRequest userAccountRequest){

        userAccountService.signup(userAccountRequest);

        return  ResponseEntity.status(200).body(
                Result.builder()
                        .message("회원가입 성공")
                        .data()
                        .build()
        );
    }

    @PostMapping("login")
    public ResponseEntity<Result> login(){

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("로그인 성공")
                        .data()
                        .build()
        );
    }
}
