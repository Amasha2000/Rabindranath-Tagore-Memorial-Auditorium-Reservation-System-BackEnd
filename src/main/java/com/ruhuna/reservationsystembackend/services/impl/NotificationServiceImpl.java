package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.entity.GuestUser;
import com.ruhuna.reservationsystembackend.entity.Notification;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.GuestUserRepository;
import com.ruhuna.reservationsystembackend.repository.NotificationRepository;
import com.ruhuna.reservationsystembackend.repository.VCRepository;
import com.ruhuna.reservationsystembackend.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final GuestUserRepository guestUserRepository;
    private final VCRepository vcRepository;
    private final AdminRepository adminRepository;

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setHasRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        Optional<GuestUser> user = guestUserRepository.findById(userId);
        return notificationRepository.findByUser(user);
    }

    @Override
    public void createNotification(String message, Long userId, String redirectUrl) {
        Notification notification = Notification.builder()
                .message(message)
                .user(guestUserRepository.findById(userId).orElseThrow())
                .date(LocalDate.now())
                .hasRead(false)
                .redirectUrl(redirectUrl)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void createVCNotification(String message, Long vcId, String redirectUrl) {
        Notification notification = Notification.builder()
                .message(message)
                .vc(vcRepository.findById(vcId).orElseThrow())
                .date(LocalDate.now())
                .hasRead(false)
                .redirectUrl(redirectUrl)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void createAdminNotification(String message, Long adminId, String redirectUrl) {
        Notification notification = Notification.builder()
                .message(message)
                .admin(adminRepository.findById(adminId).orElseThrow())
                .date(LocalDate.now())
                .hasRead(false)
                .redirectUrl(redirectUrl)
                .build();
        notificationRepository.save(notification);
    }
}
