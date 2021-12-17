package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.entity.SystemParam;
import com.squadAlertSystem.squadalertsystem.service.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/systemParams")
public class SystemParamController {

    @Autowired
    private SystemParamService systemParamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @NotNull SystemParam systemParam) {
        systemParamService.save(systemParam);
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return systemParamService.get(key);
    }

}
