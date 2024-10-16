package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.dto.common.CommonResponse;
import com.ruhuna.reservationsystembackend.services.VCService;
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
@RequestMapping("/vc")
@RequiredArgsConstructor
public class VCController {

    private final VCService vcService;

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
}