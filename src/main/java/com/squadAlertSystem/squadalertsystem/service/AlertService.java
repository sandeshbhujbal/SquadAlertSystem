package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.dto.response.PaginatedResponse;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import com.squadAlertSystem.squadalertsystem.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Milan Rathod
 */
@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public PaginatedResponse<Alert> getAll(String squad, Pageable pageable) {
        Page<Alert> pages = squad != null ?
            alertRepository.findAllBySquad(squad, pageable) : alertRepository.findAll(pageable);

        PaginatedResponse<Alert> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setContents(pages.getContent());
        paginatedResponse.setCurrentPage(pages.getNumber());
        paginatedResponse.setTotalPages(pages.getTotalPages());
        paginatedResponse.setTotalElements(pages.getTotalElements());

        return paginatedResponse;
    }
}
