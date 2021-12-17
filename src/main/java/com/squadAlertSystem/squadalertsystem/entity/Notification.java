package com.squadAlertSystem.squadalertsystem.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
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
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "notification_medium")
  private NotificationMedium notificationMedium;

  @Column(name = "summary")
  private String summary;

  @Column(name = "details")
  private String details;

  @Column(name = "sent_to")
  private String picName;

  @Column(name = "trigger_time")
  private Date triggerTime;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "alert", referencedColumnName = "id")
  private Alert alert;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Notification page = (Notification) o;
    return id != null && Objects.equals(id, page.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
