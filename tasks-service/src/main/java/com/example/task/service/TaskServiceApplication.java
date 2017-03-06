package com.example.task.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableDiscoveryClient
public class TaskServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}
}
