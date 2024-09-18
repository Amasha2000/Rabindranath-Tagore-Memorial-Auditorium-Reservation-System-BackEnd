package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/reservation")
@RequiredArgsConstructor
public class ReservationServiceController {

    private final ReservationService reservationService;

    @GetMapping("/unavailable-dates")
    public List<UnavailableDatesDto> getUnavailableDates() {
        return reservationService.getUnavailableDates();
    }
}
