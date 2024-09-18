package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;

import java.util.List;

public interface ReservationService {
    List<UnavailableDatesDto> getUnavailableDates();
}
