package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.CustomRepository.CustomSquadRepository;

public interface SquadRepository extends JpaRepository<Squad, String>, CustomSquadRepository {

  Squad findByPageId(String pagingEmailAddress);
}
