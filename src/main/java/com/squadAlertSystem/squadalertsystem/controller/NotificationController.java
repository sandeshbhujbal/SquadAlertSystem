package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.entity.Notification;
import com.squadAlertSystem.squadalertsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/fetch")
    public List<Notification> getNotificationsByUserAndMedium(@RequestParam @NotNull String user,
                                                              @RequestParam @NotNull NotificationMedium medium) {
        return notificationService.getNotificationByUserAndMedium(user, medium);
    }
}
