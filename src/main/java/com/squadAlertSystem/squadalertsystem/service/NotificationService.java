package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import com.squadAlertSystem.squadalertsystem.entity.Notification;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.service.squad.SquadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Milan Rathod
 */
@Service
@Slf4j
public class NotificationService {

    @Autowired
    private AlertService alertService;

    @Autowired
    private SquadService squadService;

    public List<Notification> getNotificationByUserAndMedium(String user, NotificationMedium medium) {

        final List<Squad> squads = squadService.getAllSquadsByMember(user);

        List<Notification> notifications = new ArrayList<>();

        squads.forEach(squad -> {
            final List<Alert> alerts = alertService.findAllBySquadAndStatus(squad.getName(), Status.OPEN);

            alerts.forEach(alert -> {
                final List<Notification> notificationList = alert.getNotifications()
                    .stream()
                    .filter(notification -> notification.getNotificationMedium().equals(medium)
                        && notification.getLastSyncAt() == null)
                    .collect(Collectors.toList());
                notifications.addAll(notificationList);
            });
        });

        return notifications;
    }
}
