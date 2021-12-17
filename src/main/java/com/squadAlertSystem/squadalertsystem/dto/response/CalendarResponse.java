package com.squadAlertSystem.squadalertsystem.dto.response;

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
public class CalendarResponse {

  private String id;

  private Date date;

  private Date startDateTime;

  private Date endDateTime;

  private String squadName;

  private List<String> picNames;

}
