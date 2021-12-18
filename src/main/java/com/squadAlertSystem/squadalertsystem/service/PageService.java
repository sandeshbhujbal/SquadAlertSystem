package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.dto.request.PageRequest;
import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.processor.PageProcessor;
import com.squadAlertSystem.squadalertsystem.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
@Slf4j
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageProcessor pageProcessor;

    public List<Page> getPages(String squad, int count) {
        return pageRepository.findAll();
    }

    public Boolean pageSquadPIC(PageRequest pageRequest) {
        try {
            Page page = buildPageEntity(pageRequest);
            pageProcessor.processPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error while paging");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Page buildPageEntity(PageRequest pageRequest) {
        return Page.builder()
            .sentBy("aman.dhaka")
            .pageTo(pageRequest.getPagingEmailAddress())
            .channel("Web")
            .createdDate(new Date())
            .severity(pageRequest.getSeverity())
            .summary(pageRequest.getSummary())
            .details(pageRequest.getDetails())
            .build();
    }
}
