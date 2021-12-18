package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, String> {

  Notification findFirstById(String notificationId);
}
