package com.squadAlertSystem.squadalertsystem.repository;

import com.squadAlertSystem.squadalertsystem.entity.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, String> {

    Page<Alert> findAllBySquad(String squad, Pageable pageable);
}
