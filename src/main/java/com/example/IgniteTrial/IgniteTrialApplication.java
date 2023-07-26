package com.example.IgniteTrial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableJms
@EnableAsync
public class IgniteTrialApplication {

	/**
	 * Hit localhost:8080(Get) to start the Aync Listner
	 */

	public static void main(String[] args) {
		SpringApplication.run(IgniteTrialApplication.class, args);


	}


}
