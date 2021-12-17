package com.squadAlertSystem.squadalertsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Milan Rathod
 */
@Data
@Configuration
@ConfigurationProperties("email")
public class EmailConfiguration {
    private String host;

    private String username;

    private String password;
}
