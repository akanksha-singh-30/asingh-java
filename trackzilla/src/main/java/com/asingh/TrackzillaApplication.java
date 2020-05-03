package com.asingh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.asingh")
public class TrackzillaApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrackzillaApplication.class, args);
	}

}
