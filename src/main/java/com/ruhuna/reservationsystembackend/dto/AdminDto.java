package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class AdminDto {
    private Long id;
    private String username;
    private String email;
    private UserRole userRole;
}
