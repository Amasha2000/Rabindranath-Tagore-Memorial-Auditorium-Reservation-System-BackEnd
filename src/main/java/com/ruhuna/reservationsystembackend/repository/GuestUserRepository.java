package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser,Long> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByUsername(String username);

    GuestUser findByUsername(String username);
    long count();
}
