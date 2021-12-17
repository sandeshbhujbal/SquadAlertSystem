package com.squadAlertSystem.squadalertsystem.entity;

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

@Builder
@Entity
@Getter
@Setter
@Table(name = "service")
@NoArgsConstructor
@AllArgsConstructor
public class Service {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "page_id")
  private String pageId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "squad", referencedColumnName = "id")
  private Squad squad;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Service service = (Service) o;
    return id != null && Objects.equals(id, service.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
