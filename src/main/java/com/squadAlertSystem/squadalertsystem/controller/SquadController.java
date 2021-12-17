package com.squadAlertSystem.squadalertsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squadAlertSystem.squadalertsystem.command.squad.SquadCommand;
import com.squadAlertSystem.squadalertsystem.dto.request.CreateSquadRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadDetailResponse;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadListingResponse;

@RestController
@RequestMapping(SquadController.BASE_URI)
public class SquadController {

  public static final String BASE_URI = "/squad";

  @Autowired
  private SquadCommand squadCommand;

  @PostMapping(path = "/create")
  public String createSquad(@RequestBody CreateSquadRequest request) {
    return squadCommand.createSquad(request);
  }

  @PostMapping(path = "/list")
  public List<SquadListingResponse> squadListing(@RequestBody SquadListingRequest request) {
    return squadCommand.listSquads(request);
  }

  @GetMapping(path = "/detail")
  public SquadDetailResponse getSquadDetails(@RequestParam String squadId) {
    return squadCommand.getSquadDetail(squadId);
  }


}
