package com.example.application.repositories;

import com.example.application.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT u FROM UserAccount u WHERE u.username = :username AND u.password = :password")
    Optional<UserAccount> verifyCredentials(@Param("username") String username, @Param("password") String password);
}


