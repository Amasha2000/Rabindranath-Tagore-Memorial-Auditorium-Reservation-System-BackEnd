package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    //List<Reservation> findByReservedDate(LocalDate date);
}
