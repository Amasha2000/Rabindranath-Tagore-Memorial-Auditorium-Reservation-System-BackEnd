package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.GuestUserDto;
import com.ruhuna.reservationsystembackend.entity.GuestUser;

public interface GuestUserService {
     void userSignUp(GuestUserDto guestUserDto);

     GuestUser findByUsername(String username);
}
