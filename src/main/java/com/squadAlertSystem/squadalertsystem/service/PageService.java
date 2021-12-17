package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.dto.request.PageRequest;
import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.processor.PageProcessor;
import com.squadAlertSystem.squadalertsystem.repository.PageRepository;
import com.squadAlertSystem.squadalertsystem.repository.ServiceRepository;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Milan Rathod
 */
@Service
@Slf4j
public class PageService {

    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageProcessor pageProcessor;

    public List<Page> getPages(String squad, int count) {
        return pageRepository.findAll();
    }

    public Boolean pageSquadPIC(PageRequest pageRequest) {
        try {
            Squad pagedSquad = squadRepository.findByPageId(pageRequest.getPagingEmailAddress());
            if (Objects.isNull(pagedSquad)) {
                pagedSquad = serviceRepository.findBySquad_PageId(pageRequest.getPagingEmailAddress());
            }
            Page page = buildPageEntity(pageRequest);
            pageProcessor.processPage(page, pagedSquad);
        } catch(Exception e) {
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
