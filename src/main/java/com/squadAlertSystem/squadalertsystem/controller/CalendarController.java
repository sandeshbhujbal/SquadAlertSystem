package com.squadAlertSystem.squadalertsystem.controller;

import java.util.Date;
import java.util.List;

import com.squadAlertSystem.squadalertsystem.service.calendar.CalendarService;
import com.squadAlertSystem.squadalertsystem.dto.request.CalendarListRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.CalendarSaveRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.CalendarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CalendarController.BASE_URI)
public class CalendarController {

  public static final String BASE_URI = "/calendar";

  @Autowired
  private CalendarService calendarService;

  @GetMapping(path = "/listCalendar")
  public List<CalendarResponse> getCalendarList(
    @RequestParam(value = "squadId") String squadId
  ) {
    return calendarService.listCalendar(toCalendarListRequest(squadId, new Date(), new Date()));
  }

  @PostMapping(path = "/saveCalendar")
  public String saveCalendar(
    @RequestBody CalendarSaveRequest request
  ) {
    return calendarService.saveCalendar(request);
  }

  CalendarListRequest toCalendarListRequest(String squadId, Date startDate, Date endDate) {
    return CalendarListRequest.builder()
      .squadId(squadId)
      .startDate(startDate)
      .endDate(endDate)
      .build();
  }

}
