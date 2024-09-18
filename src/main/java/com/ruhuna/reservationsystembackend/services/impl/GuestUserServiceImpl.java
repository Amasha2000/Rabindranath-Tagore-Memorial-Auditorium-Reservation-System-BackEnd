package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.GuestUserDto;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
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

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class GuestUserServiceImpl implements GuestUserService, UserDetailsService {

    private final GuestUserRepository guestUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void userSignUp(GuestUserDto guestUserDto) {
        try {
            if (guestUserRepository.existsByUsername(guestUserDto.getUsername())) {
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
            guestUser.setUserRole(UserRole.GUEST_USER);

            guestUserRepository.save(guestUser);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GuestUser guestUser = guestUserRepository.findByUsername(username);
        if (guestUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(guestUser.getUserRole().name());

        return new org.springframework.security.core.userdetails.User(
                guestUser.getUsername(),
                guestUser.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
