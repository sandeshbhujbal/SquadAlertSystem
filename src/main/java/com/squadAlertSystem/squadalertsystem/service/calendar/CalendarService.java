package com.squadAlertSystem.squadalertsystem.service.calendar;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
import org.springframework.util.CollectionUtils;

@Service
public class CalendarService {

  @Autowired
  private CalendarRepository calendarRepository;

  @Autowired
  SquadRepository squadRepository;

  public String saveCalendar(CalendarSaveRequest request) {

    Calendar calendar = Calendar.builder()
      .id(request.getId())
      .date(request.getDate())
      .startDateTime(request.getStartDateTime())
      .endDateTime(request.getEndDateTime())
      .build();

    if (!CollectionUtils.isEmpty(request.getPicNames())) {
      calendar.setPics(String.join(", ", request.getPicNames()));
    }

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
    return CalendarResponse.builder()
      .id(calendar.getId())
      .date(calendar.getDate())
      .startDateTime(calendar.getStartDateTime())
      .endDateTime(calendar.getEndDateTime())
      .squadName(calendar.getSquad().getName())
      .picNames(Arrays.asList(calendar.getPics().split(",")))
      .build();

  }

  public List<CalendarResponse> listCalendarByUser(String username) {
    return calendarRepository.findByPicsLike("%" + username + "%").stream()
            .map(this::toCalendarResponse).collect(Collectors.toList());
  }

}
