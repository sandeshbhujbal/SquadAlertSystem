package com.squadAlertSystem.squadalertsystem.repository;

import com.squadAlertSystem.squadalertsystem.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, String> {
}
