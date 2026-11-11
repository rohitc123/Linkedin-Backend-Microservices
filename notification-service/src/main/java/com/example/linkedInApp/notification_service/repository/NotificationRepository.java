package com.example.linkedInApp.notification_service.repository;

import com.example.linkedInApp.notification_service.entity.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface NotificationRepository extends JpaRepository<Notification,Long> {

}

