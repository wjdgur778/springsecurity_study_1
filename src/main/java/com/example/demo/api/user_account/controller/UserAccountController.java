package com.example.demo.api.user_account.controller;

import com.example.demo.api.common.Result;
import com.example.demo.api.user_account.dto.UserAccountRequest;
import com.example.demo.api.user_account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    //회원가입은 성공했지만 그 이후에 403이 리턴되었다.
    //
    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody UserAccountRequest userAccountRequest){

        userAccountService.signup(userAccountRequest);

        return  ResponseEntity.status(200).body(
                Result.builder()
                        .message("회원가입 성공")
                        .data(true)
                        .build()
        );
    }

    @PostMapping("login")
    public ResponseEntity<Result> login(){

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("로그인 성공")
                        .data(true)
                        .build()
        );
    }
    //일반 사용자만 이용 가능
    //ROLE_USER만 이용 가능
    //
    @GetMapping("list")
    public ResponseEntity<Result> list(){
        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("message")
                        .data(null)
                        .build()
        );
    }
}
