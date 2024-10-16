package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.entity.VC;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
import com.ruhuna.reservationsystembackend.repository.VCRepository;
import com.ruhuna.reservationsystembackend.services.VCService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VCServiceImpl implements  VCService {

    private final VCRepository vcRepository;
    private final AdminRepository adminRepository;
    private final GuestUserRepository guestUserRepository;
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
}
