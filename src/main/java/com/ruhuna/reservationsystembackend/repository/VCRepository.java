package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.VC;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VCRepository extends JpaRepository<VC,Long> {
    VC findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    VC findByUserRole(UserRole userRole);
    Optional<VC> findByEmail(String email);
}
