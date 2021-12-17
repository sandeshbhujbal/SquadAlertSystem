package com.squadAlertSystem.squadalertsystem.entity;

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

@Entity
public class Squad {

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

}
