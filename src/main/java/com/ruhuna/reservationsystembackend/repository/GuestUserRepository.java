package com.ruhuna.reservationsystembackend.repository;

import com.ruhuna.reservationsystembackend.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser,Long> {

}
