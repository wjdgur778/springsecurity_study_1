package com.example.demo.api.user_account.service;

import com.example.demo.api.common.Result;
import com.example.demo.api.user_account.dto.LoginRequest;
import com.example.demo.api.user_account.dto.UserAccountRequest;
import com.example.demo.api.user_account.dto.UserAccountResponse;
import com.example.demo.api.user_account.entity.Role;
import com.example.demo.api.user_account.entity.UserAccount;
import com.example.demo.api.user_account.repository.UserAccountRepository;
import com.example.demo.config.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Transactional
    public String signup(UserAccountRequest userAccountRequest) {
        //존재하는 이메일인지 확인
        //존재한다면 exception을 일으킨다.

        //builder 패턴을 통해 가독성을 높인다.
        UserAccount user = UserAccount.builder()
                .email(userAccountRequest.getUserEmail())
                .password(passwordEncoder.encode(userAccountRequest.getPassword()))
                .name(userAccountRequest.getUserName())
                .userRole(Role.GUEST)
                .build();

        userAccountRepository.save(user);
        String jwtToken = jwtService.generateToken(user.getEmail());
        return jwtToken;
    }

    @Transactional
    public String login(LoginRequest loginRequest) {
        Authentication authentication;
        try{
            // 사용자 인증 시도
            authentication = daoAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(),loginRequest.getPassword()));
        }
        catch (AuthenticationException e){
            return null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtService.generateToken(userDetails.getUsername());

        // 인증 성공 시 JWT 생성
        return jwt;
    }

    @Transactional
    public List<UserAccountResponse> show() {
        return userAccountRepository.findAll().stream().map(
                userAccount -> new UserAccountResponse(userAccount)
        ).collect(Collectors.toList());
    }

}
