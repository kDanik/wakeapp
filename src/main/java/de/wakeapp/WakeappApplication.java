package de.wakeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("de.wakeapp.repository")
public class WakeappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WakeappApplication.class, args);
	}

}
