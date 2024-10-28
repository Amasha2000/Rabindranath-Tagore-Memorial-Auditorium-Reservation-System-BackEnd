package com.ruhuna.reservationsystembackend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ResetPasswordRequestDto {
    private String token;
    private String newPassword;
}
