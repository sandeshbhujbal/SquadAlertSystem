package com.squadAlertSystem.squadalertsystem.processor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.squadAlertSystem.squadalertsystem.constant.NotificationMedium;
import com.squadAlertSystem.squadalertsystem.constant.Severity;
import com.squadAlertSystem.squadalertsystem.constant.Status;
import com.squadAlertSystem.squadalertsystem.entity.Alert;
import com.squadAlertSystem.squadalertsystem.entity.AlertConfiguration;
import com.squadAlertSystem.squadalertsystem.entity.Calendar;
import com.squadAlertSystem.squadalertsystem.entity.Notification;
import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.AlertConfigurationRepository;
import com.squadAlertSystem.squadalertsystem.repository.AlertRepository;
import com.squadAlertSystem.squadalertsystem.repository.MemberRepository;
import com.squadAlertSystem.squadalertsystem.repository.NotificationRepository;
import com.squadAlertSystem.squadalertsystem.repository.PageRepository;

@Component
public class PageProcessor {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private AlertRepository alertRepository;

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private AlertConfigurationRepository alertConfigurationRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private JavaMailSender javaMailSender;

  public void processPage(Page page, Squad squad) {
    //Save page
    page = pageRepository.save(page);
    Page finalPage = page;
    //fetch alert_configuration
    Set<AlertConfiguration> alertConfigurations = squad.getAlertConfigurations();
    //get PIC
    Calendar currentTimeCalendar = squad.getCalendars().stream()
      .filter(calendar -> filterTime(calendar.getStartDateTime(), calendar.getEndDateTime(), finalPage.getCreatedDate()))
      .findFirst()
      .get();
    Set<String> picSet = Arrays.stream(currentTimeCalendar.getPics().split(",")).collect(Collectors.toSet());
    //build Alert
    Alert alert = Alert.builder()
      .summary(page.getSummary())
      .details(page.getDetails())
      .severity(page.getSeverity())
      .squad(squad.getName())
      .status(Status.OPEN)
      .sentBy(page.getSentBy())
      .generatedDate(page.getCreatedDate())
      .sentTo(currentTimeCalendar.getPics())
      .build();
    //save alert
    alertRepository.save(alert);
    //build Notification
    List<Notification> notificationList = picSet.stream()
      .map(pic -> Notification.builder()
          .alert(alert)
          .details(finalPage.getDetails())
          .summary(finalPage.getSummary())
          .picName(pic)
          .createdDate(finalPage.getCreatedDate())
          .notificationMedium(getNotificationMedium(alertConfigurations, finalPage.getSeverity()))
          .build())
      .collect(Collectors.toList());
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

  private NotificationMedium getNotificationMedium(Set<AlertConfiguration> alertConfigurations, Severity severity) {
    return alertConfigurations.stream()
      .filter(alertConfiguration -> alertConfiguration.getSeverity().equals(severity))
      .findAny().get().getNotificationMedium();
  }

  private boolean filterTime(Date startDateTime, Date endDateTime, Date createdDate) {
    return startDateTime.compareTo(createdDate) <= 0 && endDateTime.compareTo(createdDate) >= 0;
  }


}
