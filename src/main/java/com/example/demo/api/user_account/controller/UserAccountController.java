package com.example.demo.api.user_account.controller;

import com.example.demo.api.common.Result;
import com.example.demo.api.user_account.dto.LoginRequest;
import com.example.demo.api.user_account.dto.UserAccountRequest;
import com.example.demo.api.user_account.dto.UserAccountResponse;
import com.example.demo.api.user_account.dto.WriteRequest;
import com.example.demo.api.user_account.service.UserAccountService;
import com.example.demo.config.auth.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    // 회원가입은 성공했지만 그 이후에 403이 리턴되었다.
    // 반환 data객체인 Result가 직렬화되지 않아 403 발생
    // Result에 @Data 어노테이션을 추가
    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody UserAccountRequest userAccountRequest){
        return  ResponseEntity.status(200).body(
                Result.builder()
                        .message("회원가입 성공")
                        .data(userAccountService.signup(userAccountRequest))
                        .build()
        );
    }

    //로그인 시에 jwt 토큰을 발급해주어야 한다.
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginRequest loginRequest){
        System.out.println("call - > /login");
        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("로그인 성공")
                        .data(userAccountService.login(loginRequest))
                        .build()
        );
    }
    //유저만 사용가능한 show
    @GetMapping("/list")
    public ResponseEntity<Result> list(){
        //권한이 필요한 메소드를 호출한 유저의 데이터를 SecurityContext가 가지고 있음을 알 수 있다.
        CustomUserDetail user = (CustomUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        List<UserAccountResponse> ulist = userAccountService.show();

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("message")
                        .data(ulist)
                        .build()
        );
    }

    //GUEST 이용가능
    @GetMapping("/{email}")
    public ResponseEntity<Result> getUser(@PathVariable("email") String email){
        List<UserAccountResponse> ulist = userAccountService.getUser(email);

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("message")
                        .data(ulist)
                        .build()
        );
    }

    //ROLE_USER만 이용 가능
    @PostMapping ("/write")
    public ResponseEntity<Result> write(@RequestBody WriteRequest writeRequest){

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("message")
                        .data("success writing")
                        .build()
        );
    }


    @GetMapping ("/change")
    public ResponseEntity<Result> change(){

        return ResponseEntity.status(200).body(
                Result.builder()
                        .message("message")
                        .data("success writing")
                        .build()
        );
    }
}
