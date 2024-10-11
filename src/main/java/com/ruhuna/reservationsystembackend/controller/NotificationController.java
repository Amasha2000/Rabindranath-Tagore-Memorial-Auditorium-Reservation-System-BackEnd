package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.Notification;
import com.ruhuna.reservationsystembackend.services.GuestUserService;
import com.ruhuna.reservationsystembackend.services.NotificationService;
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

    @GetMapping("/{userName}")
    public List<Notification> getNotifications(@PathVariable String userName) {
        GuestUser user = guestUserService.findByUsername(userName);
            return notificationService.getAllNotifications(user.getUser_id());
    }


    @PostMapping("/markAsRead/{notificationId}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
