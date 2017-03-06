package com.example.routing.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
// enable Netflix Eureka service discovery
@EnableZuulProxy
public class RoutingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutingServiceApplication.class, args);
	}
}
