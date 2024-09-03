package com.example.demo.api.user_account.service;

import com.example.demo.api.user_account.dto.UserAccountRequest;
import com.example.demo.api.user_account.entity.UserAccount;
import com.example.demo.api.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserAccountRequest userAccountRequest) {
        //password 인코딩을 통해 보안을 높인다.
        String password = passwordEncoder.encode(userAccountRequest.getPassword());

        //존재하는 이메일인지 확인
        //존재한다면 exception을 일으킨다.



        //builder 패턴을 통해 가독성을 높인다.
        UserAccount user = UserAccount.builder()
                .userName(userAccountRequest.getUserName())
                .userEmail(userAccountRequest.getPassword())
                .password(password)
                .build();

        userAccountRepository.save(user);
    }

}
