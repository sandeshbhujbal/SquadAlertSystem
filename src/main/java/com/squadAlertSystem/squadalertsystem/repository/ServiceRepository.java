package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, String> {

  Service findByName(String serviceName);
}
