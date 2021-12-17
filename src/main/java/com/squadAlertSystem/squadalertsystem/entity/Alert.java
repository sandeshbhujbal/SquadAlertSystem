package com.squadAlertSystem.squadalertsystem.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.squadAlertSystem.squadalertsystem.constant.Severity;
import com.squadAlertSystem.squadalertsystem.constant.Status;
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
@Table(name = "alert")
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "summary")
  private String summary;

  @Column(name = "details")
  private String details;

  @Column(name = "severity")
  private Severity severity;

  @Column(name = "squad")
  private String squad;

  @Column(name = "status")
  private Status status;

  @Column(name = "sent_by")
  private String sentBy;

  @Column(name = "generated_date")
  private long generatedDate;

  @Column(name = "sent_to")
  private List<String> sentTo;

  @Column(name = "updated_date")
  private long updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id", nullable = false)
  private Page page;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Alert page = (Alert) o;
    return id != null && Objects.equals(id, page.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
