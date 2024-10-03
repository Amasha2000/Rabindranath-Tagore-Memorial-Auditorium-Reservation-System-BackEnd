package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.ReservationDto;
import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;

import java.util.List;

public interface ReservationService {
    List<UnavailableDatesDto> getUnavailableDates();

    void submitForm(ReservationDto reservationDto);
    void updateStatus(Long reservationId);
}
