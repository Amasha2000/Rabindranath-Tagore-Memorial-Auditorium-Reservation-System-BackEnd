package com.ruhuna.reservationsystembackend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminStatDto {
    private Long numberOfUsers;
    private BigDecimal totalRevenue;
    private Long numberOfReservations;
}
