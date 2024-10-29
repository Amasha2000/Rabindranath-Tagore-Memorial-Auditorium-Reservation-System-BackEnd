package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.entity.Admin;

public interface AdminService {

    void adminSignUp(AdminDto adminDto);
    AdminStatDto getAdminStats();
    Admin findByAdminUsername(String username);
    String generateResetToken(String email) throws Exception;
    void resetPassword(String token, String newPassword) throws Exception;
    Admin updateAdminProfile(String username, Admin updatedUser);
}
