package com.squadAlertSystem.squadalertsystem.scheduler;

import com.squadAlertSystem.squadalertsystem.config.EmailConfiguration;
import com.squadAlertSystem.squadalertsystem.constant.Severity;
import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.entity.SystemParam;
import com.squadAlertSystem.squadalertsystem.processor.PageProcessor;
import com.squadAlertSystem.squadalertsystem.service.SystemParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Scheduler responsible for retrieving emails from mail server
 *
 * @author Milan Rathod
 */
@Component
@Slf4j
public class EmailRetrievalScheduler {

    private static final String LAST_EMAIL_FETCHED_AT = "LAST_EMAIL_FETCHED_AT";

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Autowired
    private SystemParamService systemParamService;

    @Autowired
    private PageProcessor pageProcessor;

//    @PostConstruct
//    public void init() {
//        fetchEmails();
//    }

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchEmails() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        try {
            Session emailSession = Session.getDefaultInstance(properties);

            // create the Imaps store object and connect with the imaps server
            Store store = emailSession.getStore("imaps");

            store.connect(emailConfiguration.getHost(), emailConfiguration.getUsername(), emailConfiguration.getPassword());

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            log.info("Numbers of emails {} ", messages.length);

            final List<Page> pages = Arrays.stream(messages)
                .filter(this::isEmailAlreadyReceived)
                .map(this::buildPageEntity)
                .collect(Collectors.toList());

            pages.forEach(page -> {
                try {
                    pageProcessor.processPage(page);
                } catch(Exception e) {
                    log.info("error while processing page from {}, {}", page.getSentBy(), e.getMessage());
                }
            });

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");

            systemParamService.save(SystemParam.builder()
                .key(LAST_EMAIL_FETCHED_AT)
                .value(dateFormat.format(new Date()))
                .build());

            // close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailAlreadyReceived(Message message) {
        final String lastEmailFetchedAt = systemParamService.get(LAST_EMAIL_FETCHED_AT);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        try {
            Date receivedDate = message.getReceivedDate();

            return lastEmailFetchedAt == null || receivedDate.after(dateFormat.parse(lastEmailFetchedAt));
        } catch (Exception ex) {
            log.error("Error occurred while parsing received date", ex);
            return false;
        }
    }

    private Page buildPageEntity(Message message) {
        try {
            // Default severity
            Severity severity = Severity.HIGH;

            // Default subject
            String subject = "";

            if (message.getSubject() != null) {
                subject = message.getSubject();

                String finalSubject = subject;
                final Optional<Severity> optionalSeverity = Arrays.stream(Severity.values()).filter(severity1 ->
                    finalSubject.toUpperCase().contains(severity1.toString())).findFirst();

                if (optionalSeverity.isPresent()) {
                    severity = optionalSeverity.get();
                    subject = subject.substring(severity.toString().length() + 3);
                }
            }

            String sentBy = new InternetAddress(message.getFrom()[0].toString()).getPersonal();

            String pageTo = new InternetAddress(message.getRecipients(Message.RecipientType.TO)[0].toString()).getAddress();

            return Page.builder()
                .sentBy(sentBy)
                .pageTo(pageTo)
                .channel("Email")
                .createdDate(new Date())
                .severity(severity)
                .summary(subject)
                .details(getDetails(message))
                .build();
        } catch (Exception ex) {
            log.error("Error occurred while building page entity", ex);
            return null;
        }
    }

    private String getDetails(Message message) throws Exception {
        StringBuilder sb = new StringBuilder();
        writePart(message, sb);
        return sb.toString();
    }

    private void writePart(Part p, StringBuilder sb) throws Exception {
        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            sb.append(p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i), sb);
        }
    }
}
