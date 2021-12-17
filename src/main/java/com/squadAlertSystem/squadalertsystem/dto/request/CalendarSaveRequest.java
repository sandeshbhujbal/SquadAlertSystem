package com.squadAlertSystem.squadalertsystem.dto.request;

import java.util.Date;
import java.util.List;

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
public class CalendarSaveRequest {

  private String id;

  private String squadId;

  private Date date;

  private Date startDateTime;

  private Date endDateTime;

  private List<String> picNames;

}
