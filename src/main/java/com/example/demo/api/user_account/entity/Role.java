package com.example.demo.api.user_account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에서는 권한 코드에 항상 ROLE_가 앞에 있어야만 한다.
// 그래서 코드별로 키 값을 ROLE_GUEST, ROLE_USER 등으로 지정해야한다.

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반사용자");

    private final String key;
    private final String title;
}
