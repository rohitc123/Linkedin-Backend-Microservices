package com.example.linkedInApp.notification_service.service;

import com.example.linkedInApp.notification_service.entity.Notification;
import com.example.linkedInApp.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {
    private final NotificationRepository notificationRepository;

    public void send(Long userId,String message){
        Notification notification=new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        Notification saved=notificationRepository.save(notification);
        log.info("Saved notification id={} for userId={}", saved.getId(), userId);
    }
}
