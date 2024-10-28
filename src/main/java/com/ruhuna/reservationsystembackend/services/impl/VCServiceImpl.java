package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.entity.*;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
import com.ruhuna.reservationsystembackend.repository.PasswordResetTokenVCRepository;
import com.ruhuna.reservationsystembackend.repository.VCRepository;
import com.ruhuna.reservationsystembackend.services.VCService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VCServiceImpl implements  VCService {

    private final VCRepository vcRepository;
    private final AdminRepository adminRepository;
    private final GuestUserRepository guestUserRepository;

    private final PasswordResetTokenVCRepository passwordResetTokenVCRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void vcSignUp(VCDto vcDto) {
        try {
            if (vcRepository.existsByUsername(vcDto.getUsername()) && adminRepository.existsByUsername(vcDto.getUsername()) && guestUserRepository.existsByUsername(vcDto.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            if (vcRepository.existsByEmail(vcDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            VC vc = modelMapper.map(vcDto, VC.class);
            if (vcDto.getUsername() != null && !vcDto.getUsername().isEmpty())
                vc.setUsername(vcDto.getUsername());
            if (vcDto.getPassword() != null && !vcDto.getPassword().isEmpty())
                vc.setPassword(passwordEncoder.encode(vcDto.getPassword()));
            if (vcDto.getEmail() != null && !vcDto.getEmail().isEmpty())
                vc.setEmail(vcDto.getEmail());
            vc.setUserRole(UserRole.ROLE_VC);

            vcRepository.save(vc);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public VC findByVCUsername(String username) {
        try {
            return vcRepository.findByUsername(username);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String generateResetToken(String email) throws Exception {
        Optional<VC> vc = vcRepository.findByEmail(email);
        if (!vc.isPresent()) {
            throw new Exception("Email not found");
        }

        VC vcUser = vc.get();
        String token = UUID.randomUUID().toString();
        PasswordResetTokenVC passwordResetToken = new PasswordResetTokenVC(token, vcUser);
        passwordResetTokenVCRepository.save(passwordResetToken);

        return token;
    }

    @Override
    public void resetPassword(String token, String newPassword) throws Exception {
        Optional<PasswordResetTokenVC> tokenOpt = passwordResetTokenVCRepository.findByToken(token);
        if (!tokenOpt.isPresent() || tokenOpt.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Invalid or expired token");
        }

        VC vc = tokenOpt.get().getVc();
        vc.setPassword(passwordEncoder.encode(newPassword));
        vcRepository.save(vc);
    }
}
