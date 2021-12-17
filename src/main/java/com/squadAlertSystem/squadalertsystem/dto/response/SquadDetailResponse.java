package com.squadAlertSystem.squadalertsystem.dto.response;

import java.util.Set;

import com.squadAlertSystem.squadalertsystem.entity.AlertConfiguration;
import com.squadAlertSystem.squadalertsystem.entity.Calendar;
import com.squadAlertSystem.squadalertsystem.entity.Member;
import com.squadAlertSystem.squadalertsystem.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SquadDetailResponse {

  private String id;

  private String pageId;

  private String name;

  private String description;

  private Set<Service> services;

  private Set<Member> members;

  private Set<AlertConfiguration> alertConfigurations;

  private Set<Calendar> calendars;

}
