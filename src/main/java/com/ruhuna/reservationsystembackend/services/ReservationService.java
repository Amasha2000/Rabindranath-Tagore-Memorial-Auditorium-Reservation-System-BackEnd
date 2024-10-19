package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.ReservationDto;
import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;

import java.util.List;

public interface ReservationService {
    List<UnavailableDatesDto> getUnavailableDates();
    List<Reservation> getAllReservations();
    void submitForm(ReservationDto reservationDto);
    void updateStatus(Long reservationId,ApprovalStatus status);
    List<Reservation> getReservationsByUsername(String username);
    void cancelReservation(Long reservationId);
    List<Reservation> findAllByStatus(ApprovalStatus status);
    List<Reservation> hasSendToVC();
    Reservation findReservationById(Long id);
    void sendToVc(Long id);
    List<Reservation> findAllByStatusToAdmin(ApprovalStatus status);
    List<Reservation> findAllByStatusToVC(ApprovalStatus status);
}
