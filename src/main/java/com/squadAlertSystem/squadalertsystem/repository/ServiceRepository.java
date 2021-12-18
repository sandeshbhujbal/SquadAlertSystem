package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Service;
import com.squadAlertSystem.squadalertsystem.entity.Squad;

public interface ServiceRepository extends JpaRepository<Service, String> {

  Service findByName(String serviceName);

  Squad findBySquad_PageId(String pagingEmailAddress);
}
