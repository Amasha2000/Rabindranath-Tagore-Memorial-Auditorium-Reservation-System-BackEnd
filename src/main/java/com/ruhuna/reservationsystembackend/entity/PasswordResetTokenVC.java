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
public class PasswordResetTokenVC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    private VC vc;
    private LocalDateTime expirationDate;

    public PasswordResetTokenVC(String token, VC vc) {
        this.token = token;
        this.vc = vc;
        this.expirationDate = LocalDateTime.now().plusHours(24);
    }
}
