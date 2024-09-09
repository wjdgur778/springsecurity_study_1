package com.example.demo.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

/*
1. SpringBoot 3.x.x로 오면서 이전에 WebSecurityConfigurerAdapter를 상속받아 configure을 구현하는 방식
대신에 SecurityFilterChain을 등록해서 람다식으로 처리하는 방식으로 구현
2.


 */


    /*
        기능 :

     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable);
        //(tomcat구동 오류) 세션 난수 문제 해결
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.sessionManagement(session ->
                session.invalidSessionStrategy(true)
                );
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers().permitAll()
                                .requestMatchers().
                )
    }
}
