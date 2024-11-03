package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.GuestUserDto;
import com.ruhuna.reservationsystembackend.entity.*;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
import com.ruhuna.reservationsystembackend.repository.PasswordResetTokenUserRepository;
import com.ruhuna.reservationsystembackend.repository.VCRepository;
import com.ruhuna.reservationsystembackend.services.EmailService;
import com.ruhuna.reservationsystembackend.services.GuestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GuestUserServiceImpl implements GuestUserService, UserDetailsService {

    private final GuestUserRepository guestUserRepository;
    private final VCRepository vcRepository;
    private final AdminRepository adminRepository;
    private final PasswordResetTokenUserRepository passwordResetTokenUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public void userSignUp(GuestUserDto guestUserDto) {
        try {
            if (guestUserRepository.existsByUsername(guestUserDto.getUsername()) && vcRepository.existsByUsername(guestUserDto.getUsername()) && adminRepository.existsByUsername(guestUserDto.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            if (guestUserRepository.existsByEmail(guestUserDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            if (guestUserRepository.existsByMobile(guestUserDto.getMobile())) {
                throw new RuntimeException("Phone number already exists");
            }

            GuestUser guestUser = modelMapper.map(guestUserDto, GuestUser.class);
            if (guestUserDto.getUsername() != null && !guestUserDto.getUsername().isEmpty())
                guestUser.setUsername(guestUserDto.getUsername());
            if (guestUserDto.getPassword() != null && !guestUserDto.getPassword().isEmpty())
                guestUser.setPassword(passwordEncoder.encode(guestUserDto.getPassword()));
            if (guestUserDto.getEmail() != null && !guestUserDto.getEmail().isEmpty())
                guestUser.setEmail(guestUserDto.getEmail());
            if (guestUserDto.getMobile() != null && !guestUserDto.getMobile().isEmpty())
                guestUser.setMobile(guestUser.getMobile());
            guestUser.setUserRole(UserRole.ROLE_GUEST_USER);

            guestUserRepository.save(guestUser);

            emailService.sendSignUpEmail(guestUser.getEmail());

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public GuestUser findByUsername(String username) {
        try {
            return guestUserRepository.findByUsername(username);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String generateResetToken(String email) throws Exception {
        Optional<GuestUser> user = guestUserRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new Exception("Email not found");
        }

        GuestUser guestUser = user.get();
        String token = UUID.randomUUID().toString();
        PasswordResetTokenUser passwordResetToken = new PasswordResetTokenUser(token, guestUser);
        passwordResetTokenUserRepository.save(passwordResetToken);

        return token;
    }

    @Override
    public void resetPassword(String token, String newPassword) throws Exception {
        Optional<PasswordResetTokenUser> tokenOpt = passwordResetTokenUserRepository.findByToken(token);
        if (!tokenOpt.isPresent() || tokenOpt.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Invalid or expired token");
        }

        GuestUser guestUser = tokenOpt.get().getUser();
        guestUser.setPassword(passwordEncoder.encode(newPassword));
        guestUserRepository.save(guestUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (guestUserRepository.findByUsername(username) != null) {
                GuestUser guestUser = guestUserRepository.findByUsername(username);

                GrantedAuthority authority = new SimpleGrantedAuthority(guestUser.getUserRole().name());

                return new org.springframework.security.core.userdetails.User(
                        guestUser.getUsername(),
                        guestUser.getPassword(),
                        Collections.singletonList(authority)
                );
            } else if(vcRepository.findByUsername(username) != null){
                VC vc = vcRepository.findByUsername(username);

                GrantedAuthority authority = new SimpleGrantedAuthority(vc.getUserRole().name());

                return new org.springframework.security.core.userdetails.User(
                        vc.getUsername(),
                        vc.getPassword(),
                        Collections.singletonList(authority)
                );
            }else if(adminRepository.findByUsername(username) != null){
                Admin admin = adminRepository.findByUsername(username);

                GrantedAuthority authority = new SimpleGrantedAuthority(admin.getUserRole().name());

                return new org.springframework.security.core.userdetails.User(
                        admin.getUsername(),
                        admin.getPassword(),
                        Collections.singletonList(authority)
                );
            }else{
                throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Override
    public GuestUser updateUserProfile(String username, GuestUser updatedUser) {
        GuestUser user = findByUsername(username);

        user.setEmail(updatedUser.getEmail());
        user.setMobile(updatedUser.getMobile());
        return guestUserRepository.save(user);
    }
}
