package com.squadAlertSystem.squadalertsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SquadListingResponse {

  private String id;

  private String name;

  private String pageId;
}
