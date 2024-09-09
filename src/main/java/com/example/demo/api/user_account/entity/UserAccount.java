package com.example.demo.api.user_account.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Builder
    public UserAccount(String email, String password, String name, Role userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }
}
