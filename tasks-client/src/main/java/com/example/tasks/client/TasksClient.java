package com.example.tasks.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/tasks/create")
@Log
public class TasksClient {

    private static String url = "http://routing-service:5555/api/tasks/v1/tasks";

    /**
     * Tenant id can be propagate through Routing service.
     * For demo we get it from oauth-service
     */
    @Autowired
    OAuthClient authClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(produces = "application/json")
    @HystrixCommand
    public ResponseEntity<?> createNewTask() {
        String taskName = UUID.randomUUID().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ authClient.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> req_payload = new HashMap<>();
        req_payload.put("name", taskName);

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if(response.getStatusCode() != HttpStatus.CREATED) {
            log.warning("Error while creating new Task " + response.getBody() );
            return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
        }

        log.info(String.format("Task with name %s created.", taskName));

        return new ResponseEntity<>(response.getHeaders().get("Location").get(0), response.getStatusCode());
    }

    /**
     * Create new Task in cache.
     */
    private ResponseEntity<?> createNewTaskfallback() {
        // cache new task
        return new ResponseEntity<>("New task created in cache", HttpStatus.ACCEPTED);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TasksClient.class, args);
    }
}
