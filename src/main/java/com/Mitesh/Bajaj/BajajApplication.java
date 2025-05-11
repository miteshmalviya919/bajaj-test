package com.Mitesh.Bajaj;

import com.Mitesh.Bajaj.service.WebhookService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BajajApplication {
	@Autowired
	private WebhookService webhookService;

	public static void main(String[] args) {
		SpringApplication.run(BajajApplication.class, args);
	}
	@PostConstruct
	public void runOnStartup() {
		webhookService.runChallengeFlow();
	}

}
