package com.example.demo.api.user_account.repository;

import com.example.demo.api.user_account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    Optional<UserAccount> findbyUserEmail(String userEmail);
}
