package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.PasswordResetTokenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenUserRepository extends JpaRepository<PasswordResetTokenUser, Long> {

    Optional<PasswordResetTokenUser> findByToken(String token);
}
