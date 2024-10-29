package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.dto.ResetPasswordRequestDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.services.AdminService;
import com.ruhuna.reservationsystembackend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final EmailService emailService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody AdminDto adminDto){;
        try {
            adminService.adminSignUp(adminDto);
            return ResponseEntity.ok(new CommonResponse<>(true, "Admin sign up successfully."));
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getAdminStats() {
        try {
            AdminStatDto adminStats = adminService.getAdminStats();
            return ResponseEntity.ok(new CommonResponse<>(true, adminStats));
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            String token = adminService.generateResetToken(email);
            String resetUrl = "http://localhost:3001/reset-password?token=" + token;
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
            adminService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new CommonResponse<>(true, "Password reset successful."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse<>(false, "Invalid or expired token."));
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Admin> getUserProfile(@PathVariable String username) {
        Admin user = adminService.findByAdminUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Admin> updateUserProfile(@PathVariable String username, @RequestBody Admin updatedUser) {
        Admin user = adminService.findByAdminUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        adminService.updateAdminProfile(username, updatedUser);
        return ResponseEntity.ok(user);
    }

}
