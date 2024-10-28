package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.PasswordResetTokenAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenAdminRepository extends JpaRepository<PasswordResetTokenAdmin, Long> {
    Optional<PasswordResetTokenAdmin> findByToken(String token);
}

