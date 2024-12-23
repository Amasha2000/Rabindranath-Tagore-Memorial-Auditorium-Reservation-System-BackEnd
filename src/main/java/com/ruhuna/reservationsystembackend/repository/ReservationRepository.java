package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.username = :username AND r.reservedDate >= :currentDate")
    List<Reservation> findUpcomingReservationsByUserName(@Param("username") String username, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT COALESCE(SUM(r.totalFee + r.advanceFee - r.refundableFee), 0) FROM Reservation r WHERE r.hasCompleted = TRUE AND r.hasCancelled = FALSE")
    BigDecimal sumTotalRevenue();
    long countByHasCompletedTrue();
    List<Reservation> findByApprovalStatus(ApprovalStatus status);
    List<Reservation> findByHasSendToVCTrue();
    Reservation findByReservationId(Long id);
    List<Reservation> findByApprovalStatusAndHasSendToVCIsFalse(ApprovalStatus status);
    List<Reservation> findByApprovalStatusAndHasSendToVCIsTrue(ApprovalStatus status);
    List<Reservation> findByApprovalStatusInAndHasCompletedFalse(List<ApprovalStatus> status);
    List<Reservation> findByApprovalStatusInAndEventTypeAndHasCompletedFalse(List<ApprovalStatus> status, String eventType);
    List<Reservation> findByHasCompletedTrue();
    List<Reservation> findByCancellationRequestedTrue();
}
