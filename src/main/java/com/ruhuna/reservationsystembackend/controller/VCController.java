package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.ResetPasswordRequestDto;
import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.entity.VC;
import com.ruhuna.reservationsystembackend.services.EmailService;
import com.ruhuna.reservationsystembackend.services.VCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/vc")
@RequiredArgsConstructor
public class VCController {

    private final VCService vcService;
    private final EmailService emailService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody VCDto vcDto){;
        try {
            vcService.vcSignUp(vcDto);
            return ResponseEntity.ok(new CommonResponse<>(true, "VC sign up successfully."));
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            String token = vcService.generateResetToken(email);
            String resetUrl = "http://localhost:3002/reset-password?token=" + token;
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
            vcService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new CommonResponse<>(true, "Password reset successful."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponse<>(false, "Invalid or expired token."));
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<VC> getVCProfile(@PathVariable String username) {
        VC user = vcService.findByVCUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<VC> updateUserProfile(@PathVariable String username, @RequestBody VC updatedUser) {
        VC user = vcService.findByVCUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        vcService.updateVCProfile(username, updatedUser);
        return ResponseEntity.ok(user);
    }
}
