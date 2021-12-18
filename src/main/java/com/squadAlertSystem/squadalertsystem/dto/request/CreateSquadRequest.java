package com.squadAlertSystem.squadalertsystem.dto.request;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.entity.AlertConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSquadRequest {

  private String id;

  private String name;

  private String description;

  private String emailId;

  private List<String> members;

  private List<String> services;

  private List<AlertConfiguration> alertConfigurations;
}
