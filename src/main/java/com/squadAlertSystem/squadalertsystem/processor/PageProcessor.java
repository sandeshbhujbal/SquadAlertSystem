package com.squadAlertSystem.squadalertsystem.processor;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.constant.Severity;
import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.entity.Calendar;
import com.squadAlertSystem.squadalertsystem.entity.*;
import com.squadAlertSystem.squadalertsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class PageProcessor {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private AlertRepository alertRepository;

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private SquadRepository squadRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private JavaMailSender javaMailSender;

  public void processPage(Page page) {
    //Save page
    page = pageRepository.save(page);
    Page finalPage = page;

    // Fetch squad
    final Squad squad = squadRepository.findByPageId(page.getPageTo());

    //fetch alert_configuration
    Set<AlertConfiguration> alertConfigurations = squad.getAlertConfigurations();
    //get PIC
    List<Calendar> currentTimeCalendar = squad.getCalendars().stream()
      .filter(calendar -> filterTime(calendar.getStartDateTime(), calendar.getEndDateTime(), finalPage.getCreatedDate()))
      .collect(Collectors.toList());
    Set<String> picSet = new HashSet<>();
    currentTimeCalendar.forEach(calendar -> {
      picSet.addAll(Arrays.stream(calendar.getPics().split(",")).collect(Collectors.toSet()));
    });
    //build Alert
    Alert alert = Alert.builder()
      .summary(page.getSummary())
      .details(page.getDetails())
      .severity(page.getSeverity())
      .squad(squad.getName())
      .status(Status.OPEN)
      .sentBy(page.getSentBy())
      .generatedDate(page.getCreatedDate())
      .sentTo(picSet.stream().collect(Collectors.joining()))
      .build();
    //save alert
    alertRepository.save(alert);

    final List<NotificationMedium> notificationMediums = getNotificationMediums(alertConfigurations, finalPage.getSeverity());

    //build Notification
    List<Notification> notificationList = new LinkedList<>();
        picSet.forEach(pic ->
            {
              final List<Notification> notifications = notificationMediums.stream()
                  .map(notificationMedium -> Notification.builder()
                      .alert(alert)
                      .details(finalPage.getDetails())
                      .summary(finalPage.getSummary())
                      .picName(pic)
                      .createdDate(finalPage.getCreatedDate())
                      .notificationMedium(notificationMedium)
                      .build()).collect(Collectors.toList());
              notificationList.addAll(notifications);
            }
        );
    //save notification
    notificationRepository.saveAll(notificationList);

    //send notification
    CompletableFuture.runAsync(() -> sendNotification(notificationList));
  }

  private void sendNotification(List<Notification> notificationList) {
    notificationList
      .stream()
      .filter(notification -> notification.getNotificationMedium().equals(NotificationMedium.EMAIL))
      .forEach(notification -> {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-reply@squadAlertSystem.com");
        simpleMailMessage.setTo(getEmailAddress(notification.getPicName()));
        simpleMailMessage.setSubject(notification.getSummary());
        simpleMailMessage.setText(notification.getDetails());
        javaMailSender.send(simpleMailMessage);
      });
  }

  private String getEmailAddress(String sentTo) {
    return memberRepository.findByName(sentTo).getEmail();
  }

  private List<NotificationMedium> getNotificationMediums(Set<AlertConfiguration> alertConfigurations, Severity severity) {
    return alertConfigurations.stream()
      .filter(alertConfiguration -> alertConfiguration.getSeverity().equals(severity))
        .map(AlertConfiguration::getNotificationMedium)
        .collect(Collectors.toList());
  }

  private boolean filterTime(Date startDateTime, Date endDateTime, Date createdDate) {
    return startDateTime.compareTo(createdDate) <= 0 && endDateTime.compareTo(createdDate) >= 0;
  }


}
