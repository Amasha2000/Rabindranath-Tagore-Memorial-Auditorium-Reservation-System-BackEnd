package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.GuestUserDto;
import com.ruhuna.reservationsystembackend.dto.ResetPasswordRequestDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.services.EmailService;
import com.ruhuna.reservationsystembackend.services.GuestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class GuestUserController {

    private final GuestUserService guestUserService;
    private final EmailService emailService;


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

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            String token = guestUserService.generateResetToken(email);
            String resetUrl = "http://localhost:3000/reset-password?token=" + token;
            emailService.sendResetPasswordEmail(email, resetUrl);
            return ResponseEntity.ok(new CommonResponse<>(true, "Reset link sent to your email."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse<>(false, "Error sending reset link."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        try {
            guestUserService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new CommonResponse<>(true, "Password reset successful."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse<>(false, "Invalid or expired token."));
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<GuestUser> getUserProfile(@PathVariable String username) {
        GuestUser user = guestUserService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GuestUser> updateUserProfile(@PathVariable String username, @RequestBody GuestUser updatedUser) {
        GuestUser user = guestUserService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        guestUserService.updateUserProfile(username, updatedUser);
        return ResponseEntity.ok(user);
    }
}

