package com.ruhuna.reservationsystembackend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class EventDto {
    private Long id;
    private String title;
    private LocalDate date;
    private String imageURL;
}
