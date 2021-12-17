package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Squad;

public interface SquadRepository extends JpaRepository<Squad, String> {
}
