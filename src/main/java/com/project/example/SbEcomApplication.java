package com.project.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbEcomApplication.class, args);
	}

}

//  ./mvnw package command is used to make an application into jar(created at target folder) we used that jar to upload in elastic beanstalk
