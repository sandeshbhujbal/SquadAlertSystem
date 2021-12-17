package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.dto.response.PaginatedResponse;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import com.squadAlertSystem.squadalertsystem.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public PaginatedResponse<Alert> getAll(String squad, Status status, Pageable pageable) {
        Page<Alert> pages = squad != null ?
            alertRepository.findAllBySquadAndStatus(squad, status, pageable) : alertRepository.findAllByStatus(status, pageable);

        PaginatedResponse<Alert> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setContents(pages.getContent());
        paginatedResponse.setCurrentPage(pages.getNumber());
        paginatedResponse.setTotalPages(pages.getTotalPages());
        paginatedResponse.setTotalElements(pages.getTotalElements());

        return paginatedResponse;
    }

    public List<Alert> findAllBySquadAndStatus(String squad, Status status) {
        Pageable pageable = PageRequest.of(1, Integer.MAX_VALUE);
        final Page<Alert> alertPages = alertRepository.findAllBySquadAndStatus(squad, status, pageable);

        return alertPages.getContent();
    }
}
