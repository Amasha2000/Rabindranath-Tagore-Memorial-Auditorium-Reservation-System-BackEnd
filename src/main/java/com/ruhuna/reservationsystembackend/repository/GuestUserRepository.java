package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser,Long> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByUsername(String username);

    GuestUser findByUsername(String username);
    long count();
    Optional<GuestUser> findByEmail(String email);
}
