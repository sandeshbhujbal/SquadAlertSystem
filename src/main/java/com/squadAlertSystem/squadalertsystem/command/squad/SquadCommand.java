package com.squadAlertSystem.squadalertsystem.command.squad;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.dto.request.CreateSquadRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadDetailResponse;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadListingResponse;

public interface SquadCommand {

  String createSquad(CreateSquadRequest request);

  List<SquadListingResponse> listSquads(SquadListingRequest request);

  SquadDetailResponse getSquadDetail(String squadId);
}
