package com.example.demo.api.user_account.dto;

import com.example.demo.api.user_account.entity.UserAccount;
import lombok.Data;

@Data
public class UserAccountResponse {
    String email;
    String name;

    public UserAccountResponse(UserAccount userAccount){
        this.email = userAccount.getEmail();
        this.name = userAccount.getName();
    }

}
