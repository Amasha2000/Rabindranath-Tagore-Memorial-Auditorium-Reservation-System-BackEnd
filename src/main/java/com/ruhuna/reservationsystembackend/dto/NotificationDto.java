package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class NotificationDto {
    private Long id;
    private String message;
    private UserRole userRole;
    private LocalDate date;
}
