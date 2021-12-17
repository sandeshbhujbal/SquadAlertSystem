package com.squadAlertSystem.squadalertsystem.entity;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.squadAlertSystem.squadalertsystem.dto.response.SquadDetailResponse;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadListingResponse;
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
@Table(name = "squad")
@NoArgsConstructor
@AllArgsConstructor
public class Squad {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "page_id")
  private String pageId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "squad", fetch = FetchType.LAZY)
  private Set<Service> services;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "squad_member_mapping",
    joinColumns = {@JoinColumn(name = "squad_id")},
    inverseJoinColumns = {@JoinColumn(name = "member_id")}
  )
  private Set<Member> members;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "squad_alert_configuration_mapping",
    joinColumns = {@JoinColumn(name = "squad_id")},
    inverseJoinColumns = {@JoinColumn(name = "alert_configuration_id")}
  )
  private Set<AlertConfiguration> alertConfigurations;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "squad", fetch = FetchType.LAZY)
  private Set<Calendar> calendars;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Squad squad = (Squad) o;
    return id != null && Objects.equals(id, squad.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public static SquadListingResponse toSquadListingResponse(Squad squad) {
    return SquadListingResponse.builder()
      .id(squad.getId())
      .name(squad.getName())
      .pageId(squad.getPageId())
      .build();
  }
}
