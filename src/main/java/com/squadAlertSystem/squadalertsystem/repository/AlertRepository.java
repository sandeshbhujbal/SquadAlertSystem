package com.squadAlertSystem.squadalertsystem.repository;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, String> {

    Page<Alert> findAllBySquad(String squad, Pageable pageable);

    Page<Alert> findAllBySquadAndStatus(String squad, Status status, Pageable pageable);

    Page<Alert> findAllByStatus(Status status, Pageable pageable);

    List<Alert> findAllBySentToIgnoreCaseContaining(String username);
}
