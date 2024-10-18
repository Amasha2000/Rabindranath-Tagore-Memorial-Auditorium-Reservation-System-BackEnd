package com.ruhuna.reservationsystembackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(nullable = false)
    private String message;
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false, columnDefinition = "tinyint(1)")
    private boolean hasRead;
    @Column
    private String redirectUrl;

    @ManyToOne
    @JoinColumn(name = "adminId")
    @JsonIgnore
    private Admin admin;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private GuestUser user;
    @ManyToOne
    @JoinColumn(name = "vcId")
    @JsonIgnore
    private VC vc;
}
