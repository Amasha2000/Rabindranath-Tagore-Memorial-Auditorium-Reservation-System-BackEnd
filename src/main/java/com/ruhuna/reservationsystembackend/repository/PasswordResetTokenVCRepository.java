package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.PasswordResetTokenVC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenVCRepository extends JpaRepository<PasswordResetTokenVC, Long> {
    Optional<PasswordResetTokenVC> findByToken(String token);
}
