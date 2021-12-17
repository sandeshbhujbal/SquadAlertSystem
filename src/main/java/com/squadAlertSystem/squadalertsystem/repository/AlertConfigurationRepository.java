package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.AlertConfiguration;

public interface AlertConfigurationRepository extends JpaRepository<AlertConfiguration, String> {
}
