package com.squadAlertSystem.squadalertsystem.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SquadListingRequest {

  private List<Filter> filters;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Filter {

    private String field;

    private String value;
  }
}
