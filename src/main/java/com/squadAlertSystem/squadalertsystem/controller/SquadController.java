package com.squadAlertSystem.squadalertsystem.controller;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.dto.request.AddMemberRequest;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.service.squad.SquadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squadAlertSystem.squadalertsystem.dto.request.CreateSquadRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadDetailResponse;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadListingResponse;

@RestController
@RequestMapping(SquadController.BASE_URI)
@CrossOrigin
public class SquadController {

  public static final String BASE_URI = "/squad";

  @Autowired
  private SquadService squadService;

  @PostMapping(path = "/create")
  public String createSquad(@RequestBody CreateSquadRequest request) {
    return squadService.createSquad(request);
  }

  @PostMapping(path = "/list")
  public List<SquadListingResponse> squadListing(@RequestBody SquadListingRequest request) {
    return squadService.listSquads(request);
  }

  @GetMapping(path = "/detail")
  public SquadDetailResponse getSquadDetails(@RequestParam String squadId) {
    return squadService.getSquadDetail(squadId);
  }

  @PostMapping(path = "/add-member")
  public String addMember(@RequestBody AddMemberRequest request) {
    return squadService.addMember(request);
  }

  @GetMapping("/get-all-by-member")
  public List<Squad> getAllSquadByMember(@RequestParam String member) {
    return squadService.getAllSquadsByMember(member);
  }

}
