package com.squadAlertSystem.squadalertsystem.controller;

import java.util.List;

import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.dto.response.PaginatedResponse;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import com.squadAlertSystem.squadalertsystem.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/list")
    public PaginatedResponse<Alert> getAll(@RequestParam(required = true) String squad,
                                           @RequestParam(required = false) Status status,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alertService.getAll(squad, status, pageable);
    }

    @GetMapping("/user-list")
    public List<Alert> getUserAlerts(
      @RequestParam String username) {
        return alertService.getUserAlerts(username);
    }

    @PostMapping("/acknowledge")
    public Boolean acknowledgeAlert(@RequestParam String alertId, @RequestParam Status status) {
        return alertService.acknowledgeAlert(alertId, status);
    }

}
