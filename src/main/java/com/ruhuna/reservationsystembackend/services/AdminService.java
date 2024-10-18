package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;

public interface AdminService {

    void adminSignUp(AdminDto adminDto);
    AdminStatDto getAdminStats();

}
