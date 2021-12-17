package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    public List<Page> getPages(String squad, int count) {
        return pageRepository.findAll();
    }
}
