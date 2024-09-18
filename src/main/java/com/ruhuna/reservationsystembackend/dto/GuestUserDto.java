package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class GuestUserDto {
    private Long id;
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    private String mobile;
    private UserRole userRole;
}
