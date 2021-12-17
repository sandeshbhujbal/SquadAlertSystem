package com.squadAlertSystem.squadalertsystem.repository.CustomRepository;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.entity.Squad;

public interface CustomSquadRepository {

  List<Squad> listSquads(SquadListingRequest request);
}
