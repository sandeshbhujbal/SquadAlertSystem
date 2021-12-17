package com.squadAlertSystem.squadalertsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.CustomRepository.CustomSquadRepository;

public interface SquadRepository extends JpaRepository<Squad, String>, CustomSquadRepository {
}
