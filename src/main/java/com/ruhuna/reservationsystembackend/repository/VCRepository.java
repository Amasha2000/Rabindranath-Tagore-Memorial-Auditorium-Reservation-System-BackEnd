package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.VC;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VCRepository extends JpaRepository<VC,Long> {
    VC findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    VC findByUserRole(UserRole userRole);
}
