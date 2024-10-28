package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Admin findByUserRole(UserRole userRole);

    Optional<Admin> findByEmail(String email);
}
