package com.squadAlertSystem.squadalertsystem.service;

import com.squadAlertSystem.squadalertsystem.entity.SystemParam;
import com.squadAlertSystem.squadalertsystem.repository.SystemParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Milan Rathod
 */
@Service
public class SystemParamService {

    @Autowired
    private SystemParamRepository systemParamRepository;

    public void save(SystemParam systemParam) {
        systemParamRepository.save(systemParam);
    }

    public String get(String key) {
        final Optional<SystemParam> optionalSystemParam = systemParamRepository
            .findById(key);

        return optionalSystemParam.map(SystemParam::getValue).orElse(null);
    }

}
