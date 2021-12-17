package com.squadAlertSystem.squadalertsystem.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.constant.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Entity
@Getter
@Setter
@Table(name = "alert_configuration")
@NoArgsConstructor
@AllArgsConstructor
public class AlertConfiguration {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "severity")
  private Severity severity;

  @Column(name = "notification_medium")
  private NotificationMedium notificationMedium;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    AlertConfiguration that = (AlertConfiguration) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
