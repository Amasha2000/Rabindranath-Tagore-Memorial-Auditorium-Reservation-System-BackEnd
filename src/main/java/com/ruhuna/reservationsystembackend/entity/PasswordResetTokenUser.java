package com.ruhuna.reservationsystembackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PasswordResetTokenUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    private GuestUser user;
    private LocalDateTime expirationDate;

    public PasswordResetTokenUser(String token, GuestUser user) {
        this.token = token;
        this.user = user;
        this.expirationDate = LocalDateTime.now().plusHours(24);
    }
}
