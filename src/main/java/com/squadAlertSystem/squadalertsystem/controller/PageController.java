package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.dto.request.PageRequest;
import com.squadAlertSystem.squadalertsystem.entity.Page;
import com.squadAlertSystem.squadalertsystem.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/page")
@CrossOrigin
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/get")
    public List<Page> fetchEmails(@RequestParam String emailAddress, @RequestParam int count) {
        return pageService.getPages(emailAddress, count);
    }

    @PostMapping
    public Boolean pageSquad(@RequestBody PageRequest pageRequest) {
        return pageService.pageSquadPIC(pageRequest);
    }
}
