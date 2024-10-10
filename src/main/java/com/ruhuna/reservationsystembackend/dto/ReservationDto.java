package com.ruhuna.reservationsystembackend.dto;

import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ReservationDto {
    private Long id;
    private String organizationName;
    private String applicantName;
    private String nic;
    private String email;
    private String mobile;
    private String landLine;
    private String address;
    private String eventType;
    private String otherEventType;
    private String concertType;
    private String musicBand;
    private String singers;
    private String specialInvitees;
    private String viewers;
    private ApprovalStatus approvalStatus;
    private LocalDate reservedDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private Integer eventNoOfHours;
    private Integer eventAdditionalHours;
    private LocalTime decorationStartTime;
    private LocalTime decorationEndTime;
    private Integer decorationNoOfHours;
    private LocalTime rehearsalStartTime;
    private LocalTime rehearsalEndTime;
    private Integer rehearsalNoOfHours;
    private String stageLighting;
    private String stageSoundAdministration;
    private String electricGenerator;
    private String stageDecoration;
    private String ticketSalesAtPremises;
    private String security;
    private String username;
}
