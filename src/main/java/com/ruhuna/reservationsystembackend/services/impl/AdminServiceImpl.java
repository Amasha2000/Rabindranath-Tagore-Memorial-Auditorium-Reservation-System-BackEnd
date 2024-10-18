package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
import com.ruhuna.reservationsystembackend.repository.ReservationRepository;
import com.ruhuna.reservationsystembackend.repository.VCRepository;
import com.ruhuna.reservationsystembackend.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final VCRepository vcRepository;

    private final AdminRepository adminRepository;

    private final GuestUserRepository guestUserRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


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
            admin.setUserRole(UserRole.ROLE_VC);

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
}
