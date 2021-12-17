package com.squadAlertSystem.squadalertsystem.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "page")
@NoArgsConstructor
@AllArgsConstructor
public class Page {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "channel")
  private String channel;

  @Column(name = "severity")
  private Severity severity;

  @Column(name = "summary")
  private String summary;

  @Column(name = "details")
  private String details;

  @Column(name = "sent_by")
  private String sentBy;

  @Column(name = "created_date")
  private Date createdDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Page page = (Page) o;
    return id != null && Objects.equals(id, page.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
