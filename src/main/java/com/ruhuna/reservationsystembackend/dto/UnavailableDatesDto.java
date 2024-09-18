package com.ruhuna.reservationsystembackend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class UnavailableDatesDto {
    private LocalDate reservedDate;
    private String eventType;
    private String organizationName;
}
