package com.manage.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = {
		"com.SmartFitAI.controller.user",
		"com.SmartFitAI.controller.auth",
		"com.SmartFitAI.controller.info",
		"com.SmartFitAI.controller.contact",
		"com.SmartFitAI.controller.exercise",
		"com.SmartFitAI.controller.program",
		"com.SmartFitAI.dto",
		"com.SmartFitAI.model",
		"com.SmartFitAI.service.User",
		"com.SmartFitAI.service.Info",
		"com.SmartFitAI.service.Contact",
		"com.SmartFitAI.service.Exercise",
		"com.SmartFitAI.service.Program",
		"com.manage.configuration.db",
		"com.manage.configuration.security"
})
@EnableMongoRepositories(basePackages = {"com.SmartFitAI.repository"})
@SpringBootApplication()
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("System is running...");
	}

}


