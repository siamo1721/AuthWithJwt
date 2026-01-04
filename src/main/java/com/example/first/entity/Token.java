package com.example.first.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash_token", nullable = false)
    private String hashToken;

    @Column(name = "token_expiration", nullable = false)
    private Instant expiration;

    @Column(name = "active_token", nullable = false)
    private boolean activeToken;

    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne
    private User user;

}
