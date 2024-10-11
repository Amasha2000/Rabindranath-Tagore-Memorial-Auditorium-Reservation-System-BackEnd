package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.entity.Notification;

import java.util.List;

public interface NotificationService {

    void markAsRead(Long notificationId);

    List<Notification> getAllNotifications(Long userId);

    void createNotification(String message, Long userId, String redirectUrl);
}
