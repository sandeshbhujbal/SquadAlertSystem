package com.squadAlertSystem.squadalertsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SquadAlertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquadAlertSystemApplication.class, args);
	}

}
