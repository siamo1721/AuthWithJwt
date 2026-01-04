package com.example.first.repository;

import com.example.first.entity.Token;
import com.example.first.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUser(User user);

    Optional<Token> findByUserAndActiveTokenTrue(User user);

}
