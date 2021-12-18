package com.squadAlertSystem.squadalertsystem.dto.request;

import com.squadAlertSystem.squadalertsystem.constant.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

  private String pagingEmailAddress;

  private Severity severity;

  private String summary;

  private String details;
}

