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
@Table(name = "calendar")
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "date")
  private Date date;

  @Column(name = "start_time")
  private Date startDateTime;

  @Column(name = "end_time")
  private Date endDateTime;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "squad", referencedColumnName = "id")
  private Squad squad;

  @Column(name = "pics")
  private String pics;

  @Column(name = "watcher")
  private String watchers;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Calendar calendar = (Calendar) o;
    return id != null && Objects.equals(id, calendar.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
