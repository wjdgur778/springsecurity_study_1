package com.example.demo.api.user_account.dto;

import com.example.demo.api.user_account.entity.Role;
import lombok.Getter;

@Getter
public class UserAccountRequest {

    String userEmail;
    String userName;
    String password;

}
