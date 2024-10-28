package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.*;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import com.ruhuna.reservationsystembackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    //get all pending reservations
    @GetMapping("/get/pending")
    public ResponseEntity<List<Reservation>> getPendingReservations() {
        List<Reservation> reservations = reservationService.findAllByStatus(ApprovalStatus.PENDING);
        return ResponseEntity.ok(reservations);
    }

    //get all pending reservations for admin
    @GetMapping("/get/admin-pending")
    public ResponseEntity<List<Reservation>> getPendingReservationsForAdmin() {
        List<Reservation> reservations = reservationService.findAllByStatusToAdmin(ApprovalStatus.PENDING);
        return ResponseEntity.ok(reservations);
    }

    //get all pending reservations for vc
    @GetMapping("/get/vc-pending")
    public ResponseEntity<List<Reservation>> getPendingReservationsForVC() {
        List<Reservation> reservations = reservationService.findAllByStatusToVC(ApprovalStatus.PENDING);
        return ResponseEntity.ok(reservations);
    }

    //get all reservations for sending vc
    @GetMapping("/get/send")
    public ResponseEntity<List<Reservation>> getSendingVCReservations() {
        List<Reservation> reservations = reservationService.hasSendToVC();
        return ResponseEntity.ok(reservations);
    }

    //get all by id
    @GetMapping("/get/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservation);
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

    @PutMapping(value = "/send-vc/{id}")
    public ResponseEntity<?> sendToVC(@PathVariable Long id){
        try{
            reservationService.sendToVc(id);
            return ResponseEntity.ok(new CommonResponse<>(true,"Successfully send to VC"));
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    //update approval status
    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long id,@PathVariable ApprovalStatus status){
        try {
            reservationService.updateStatus(id,status);
            return ResponseEntity.ok(new CommonResponse<>(true, "Approval Status has changed successfully"));
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/by-status-eventType")
    public ResponseEntity<List<Reservation>> getReservationsByStatusAndEventType(
            @RequestParam List<ApprovalStatus> status,
            @RequestParam(required = false) String eventType) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByStatusAndEventType(status, eventType);
            return ResponseEntity.ok(reservations);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @PutMapping("/complete/{reservationId}")
    public ResponseEntity<Void> completeReservation(@PathVariable Long reservationId) {
        reservationService.completeReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/complete")
    public ResponseEntity<List<Reservation>> getCompletedReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllCompletedReservations();
            return ResponseEntity.ok(reservations);
        }catch(RuntimeException e){
            throw e;
        }
    }
}

