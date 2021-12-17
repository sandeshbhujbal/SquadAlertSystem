package com.squadAlertSystem.squadalertsystem.service.Calendar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.squadAlertSystem.squadalertsystem.dto.request.CalendarListRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.CalendarResponse;
import com.squadAlertSystem.squadalertsystem.entity.Calendar;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarListCommand {

  @Autowired
  SquadRepository squadRepository;

  public List<CalendarResponse> listCalendar(CalendarListRequest request) {
    Squad squad = squadRepository.findById(request.getSquadId()).get();
    return squad.getCalendars().stream().map(this::toCalendarResponse).collect(Collectors.toList());
  }

  private CalendarResponse toCalendarResponse(Calendar calendar) {
    CalendarResponse calendarResponse = new CalendarResponse();
    BeanUtils.copyProperties(calendar, calendarResponse);
    calendarResponse.setPics(Arrays.asList(calendar.getPics().split(",")));
    calendarResponse.setWatchers(Arrays.asList(calendar.getWatchers().split(",")));
    return calendarResponse;
  }

}
