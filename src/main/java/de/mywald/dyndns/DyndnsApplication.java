package de.mywald.dyndns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DyndnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DyndnsApplication.class, args);
	}
}
