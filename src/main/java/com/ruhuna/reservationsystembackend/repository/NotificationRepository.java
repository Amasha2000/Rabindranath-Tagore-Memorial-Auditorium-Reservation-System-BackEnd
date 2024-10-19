package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.Notification;
import com.ruhuna.reservationsystembackend.entity.VC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserAndHasReadFalse(Optional<GuestUser> user);

    List<Notification> findByUser(Optional<GuestUser> user);

    List<Notification> findByVc(Optional<VC> user);

    List<Notification> findByAdmin(Optional<Admin> user);
}
