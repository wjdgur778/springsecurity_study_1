package com.example.demo.api.user_account.repository;


import com.example.demo.api.user_account.entity.QUserAccount;
import com.example.demo.api.user_account.entity.UserAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccountRepoImpl {
    private final JPAQueryFactory queryFactory;

    public UserAccountRepoImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<UserAccount> findByEmail(String email) {

        QUserAccount qUserAccount = QUserAccount.userAccount;
        return queryFactory.selectFrom(qUserAccount)
                .where(qUserAccount.email.eq(email))
                .fetch();
    }
}
