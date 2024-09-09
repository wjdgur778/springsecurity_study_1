package com.example.demo.config.auth;

import com.example.demo.api.user_account.entity.UserAccount;
import com.example.demo.api.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*

 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;



    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(userEmail)
                .orElseThrow(
                        //나중에 exception 추가하기
                        ()->new RuntimeException()
                );
        return new CustomUserDetail(userAccount);
    }
}
