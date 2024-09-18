package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.GuestUserDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.services.GuestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class GuestUserController {

    private final GuestUserService guestUserService;


    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody GuestUserDto guestUserDto){
        System.out.println("Sign up");
        try {
            System.out.println(guestUserDto);
            guestUserService.userSignUp(guestUserDto);
            return ResponseEntity.ok(new CommonResponse<>(true, "User sign up successfully."));
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}

