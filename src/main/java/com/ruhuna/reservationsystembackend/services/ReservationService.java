package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.ReservationDto;
import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<UnavailableDatesDto> getUnavailableDates();
    List<Reservation> getAllReservations();
    void submitForm(ReservationDto reservationDto);
    void updateStatus(Long reservationId);
    List<Reservation> getReservationsByUsername(String username);
    void cancelReservation(Long reservationId);
}
