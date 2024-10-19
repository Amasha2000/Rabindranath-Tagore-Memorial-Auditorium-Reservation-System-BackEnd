package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.entity.Admin;

public interface AdminService {

    void adminSignUp(AdminDto adminDto);
    AdminStatDto getAdminStats();
    Admin findByAdminUsername(String username);

}
