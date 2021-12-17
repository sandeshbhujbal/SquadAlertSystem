package com.squadAlertSystem.squadalertsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.constant.Severity;

@Entity
public class AlertConfiguration {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "severity")
  private Severity severity;

  @Column(name = "notification_medium")
  private NotificationMedium notificationMedium;

}
