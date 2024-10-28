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
public class PasswordResetTokenAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    private Admin admin;
    private LocalDateTime expirationDate;

    public PasswordResetTokenAdmin(String token, Admin admin) {
        this.token = token;
        this.admin = admin;
        this.expirationDate = LocalDateTime.now().plusHours(24);
    }
}
