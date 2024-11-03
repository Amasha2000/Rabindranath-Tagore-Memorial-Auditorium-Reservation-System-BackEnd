package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.PasswordResetTokenAdmin;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.*;
import com.ruhuna.reservationsystembackend.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final VCRepository vcRepository;
    private final AdminRepository adminRepository;
    private final GuestUserRepository guestUserRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenAdminRepository passwordResetTokenAdminRepository;


    @Override
    public void adminSignUp(AdminDto adminDto) {
        try {
            if (adminRepository.existsByUsername(adminDto.getUsername()) && vcRepository.existsByUsername(adminDto.getUsername()) && guestUserRepository.existsByUsername(adminDto.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            if (adminRepository.existsByEmail(adminDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            Admin admin = modelMapper.map(adminDto, Admin.class);
            if (adminDto.getUsername() != null && !adminDto.getUsername().isEmpty())
                admin.setUsername(adminDto.getUsername());
            if (adminDto.getPassword() != null && !adminDto.getPassword().isEmpty())
                admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            if (adminDto.getEmail() != null && !adminDto.getEmail().isEmpty())
                admin.setEmail(adminDto.getEmail());
            admin.setUserRole(UserRole.ROLE_ADMIN);

            adminRepository.save(admin);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public AdminStatDto getAdminStats() {
        long totalUsers = guestUserRepository.count();
        BigDecimal totalRevenue = reservationRepository.sumTotalRevenue();
        long totalCompletedReservations = reservationRepository.countByHasCompletedTrue();

        return new AdminStatDto(totalUsers, totalRevenue, totalCompletedReservations);
    }

    @Override
    public Admin findByAdminUsername(String username) {
        try {
            return adminRepository.findByUsername(username);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String generateResetToken(String email) throws Exception {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (!admin.isPresent()) {
            throw new Exception("Email not found");
        }

        Admin adminUser = admin.get();
        String token = UUID.randomUUID().toString();
        PasswordResetTokenAdmin passwordResetToken = new PasswordResetTokenAdmin(token, adminUser);
        passwordResetTokenAdminRepository.save(passwordResetToken);

        return token;
    }

   @Override
    public void resetPassword(String token, String newPassword) throws Exception {
        Optional<PasswordResetTokenAdmin> tokenOpt = passwordResetTokenAdminRepository.findByToken(token);
        if (!tokenOpt.isPresent() || tokenOpt.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Invalid or expired token");
        }

        Admin adminUser = tokenOpt.get().getAdmin();
        adminUser.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(adminUser);
    }

    @Override
    public Admin updateAdminProfile(String username, Admin updatedUser) {
        Admin user = findByAdminUsername(username);

        user.setEmail(updatedUser.getEmail());

        return adminRepository.save(user);
    }
}
