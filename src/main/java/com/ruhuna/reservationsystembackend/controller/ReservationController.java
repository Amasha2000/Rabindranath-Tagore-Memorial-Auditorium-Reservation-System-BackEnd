package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.ReservationDto;
import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    //get reserved date details
    @GetMapping("/unavailable-dates")
    public List<UnavailableDatesDto> getUnavailableDates() {
        return reservationService.getUnavailableDates();
    }

   //get all reservations
    @GetMapping("/get_all")
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    //get reservation by user Id
    @GetMapping("/get_user/{username}")
    public ResponseEntity<List<Reservation>> getReservationByUserName(@PathVariable String username) {
        // Fetch reservations by username
        List<Reservation> reservations = reservationService.getReservationsByUsername(username);

        return ResponseEntity.ok(reservations);
    }


    //form submission
    @PostMapping(value = "/submit-form",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitForm(@Valid @RequestBody ReservationDto reservationDto){
        try{
            reservationService.submitForm(reservationDto);
            return ResponseEntity.ok(new CommonResponse<>(true,"Form submitted successfully"));
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    //update approval status
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long id){
        try {
            reservationService.updateStatus(id);
            return ResponseEntity.ok(new CommonResponse<>(true, "Approval Status has changed successfully"));
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        }
}
