package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserAndHasReadFalse(Optional<GuestUser> user);

    List<Notification> findByUser(Optional<GuestUser> user);
}
