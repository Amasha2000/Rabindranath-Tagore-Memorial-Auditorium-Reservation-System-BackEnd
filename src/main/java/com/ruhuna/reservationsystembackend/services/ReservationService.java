package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.*;
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
    List<Reservation> getReservationsByStatusAndEventType(List<ApprovalStatus> status, String eventType);
    void completeReservation(Long reservationId);
    List<Reservation> getAllCompletedReservations();
    void requestCancellation(Long reservationId);
    void approveCancellation(Long reservationId);
    void rejectCancellation(Long reservationId);
    List<Reservation> getAllCancellationRequestedReservations();
}
