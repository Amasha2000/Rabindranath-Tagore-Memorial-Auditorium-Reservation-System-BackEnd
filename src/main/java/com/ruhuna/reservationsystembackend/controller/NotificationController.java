package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.Notification;
import com.ruhuna.reservationsystembackend.entity.VC;
import com.ruhuna.reservationsystembackend.services.AdminService;
import com.ruhuna.reservationsystembackend.services.GuestUserService;
import com.ruhuna.reservationsystembackend.services.NotificationService;
import com.ruhuna.reservationsystembackend.services.VCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final GuestUserService guestUserService;

    private final VCService vcService;

    private final AdminService adminService;

    @GetMapping("/{userName}")
    public List<Notification> getNotifications(@PathVariable String userName) {
        try {
            GuestUser user = guestUserService.findByUsername(userName);
            return notificationService.getAllNotifications(user.getUserId());
        }catch(RuntimeException e){
            throw e;
        }
    }

    @GetMapping("/vc/{userName}")
    public List<Notification> getVCNotifications(@PathVariable String userName) {
        try{
        VC user = vcService.findByVCUsername(userName);
        return notificationService.getAllVCNotifications(user.getVcId());
        }catch(RuntimeException e){
            throw e;
        }
    }

    @GetMapping("/admin/{userName}")
    public List<Notification> getAdminNotifications(@PathVariable String userName) {
        try {
            Admin user = adminService.findByAdminUsername(userName);
            return notificationService.getAllAdminNotifications(user.getAdminId());
        }catch(RuntimeException e){
        throw e;
        }
    }


    @PostMapping("/markAsRead/{notificationId}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
