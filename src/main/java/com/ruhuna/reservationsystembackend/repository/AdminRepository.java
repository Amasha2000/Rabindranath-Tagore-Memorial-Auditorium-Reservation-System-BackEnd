package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}