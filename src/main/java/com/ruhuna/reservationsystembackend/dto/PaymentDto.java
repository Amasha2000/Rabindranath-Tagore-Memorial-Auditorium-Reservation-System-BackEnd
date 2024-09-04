package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.PaymentType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PaymentDto {
    private Long id;
    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private PaymentType paymentType;
    private BigDecimal amount;
}
