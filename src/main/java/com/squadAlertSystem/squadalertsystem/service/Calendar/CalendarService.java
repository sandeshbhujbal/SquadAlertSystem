package com.squadAlertSystem.squadalertsystem.service.Calendar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.squadAlertSystem.squadalertsystem.dto.request.CalendarListRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.CalendarSaveRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.CalendarResponse;
import com.squadAlertSystem.squadalertsystem.entity.Calendar;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.CalendarRepository;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

  @Autowired
  private CalendarRepository calendarRepository;

  @Autowired
  SquadRepository squadRepository;

  public String saveCalendar(CalendarSaveRequest request) {

    Calendar calendar = new Calendar();
    BeanUtils.copyProperties(request, calendar);
    calendar.setPics(String.join(", ", request.getPics()));
    calendar.setPics(String.join(", ", request.getWatchers()));

    Squad squad = squadRepository.findById(request.getSquadId()).get();
    calendar.setSquad(squad);
    Calendar calendarAfterSave = calendarRepository.save(calendar);

    return calendarAfterSave.getId();
  }

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
