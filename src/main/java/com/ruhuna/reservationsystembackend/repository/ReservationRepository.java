package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.username = :username")
    List<Reservation> findByGuestUsername(@Param("username") String username);;
}
