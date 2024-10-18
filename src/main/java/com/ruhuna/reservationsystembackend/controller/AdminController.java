package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.AdminDto;
import com.ruhuna.reservationsystembackend.dto.AdminStatDto;
import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.services.AdminService;
import com.ruhuna.reservationsystembackend.services.VCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

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
}
