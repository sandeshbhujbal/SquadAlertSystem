package com.squadAlertSystem.squadalertsystem.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarListRequest {

  private String squadId;

  private Date startDate;

  private Date endDate;

}
