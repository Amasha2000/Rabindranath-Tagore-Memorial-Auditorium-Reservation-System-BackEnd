package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.repository.ReservationRepository;
import com.ruhuna.reservationsystembackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<UnavailableDatesDto> getUnavailableDates() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            return reservations.stream()
                    .map(reservation -> new UnavailableDatesDto(
                            reservation.getReservedDate(),
                            reservation.getOrganizationName(),
                            reservation.getEventType()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }
}
