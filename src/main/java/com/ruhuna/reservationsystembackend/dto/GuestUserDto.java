package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class GuestUserDto {
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private UserRole userRole;
}
