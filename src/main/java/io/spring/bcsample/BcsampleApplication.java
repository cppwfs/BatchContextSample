package io.spring.bcsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class BcsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcsampleApplication.class, args);
	}

}
