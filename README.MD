Tasks microservice (Spring Boot, MVC, Data, Lombok, Cloud, OAuth2, Config, Eureka, Zuul, Hystrix, Docker)
=========================================================================================================

*Resource is a Task*

Some core use cases for a Task Service are

Use cases
=========

1. As a API consumer, I want to create a task.
2. As a API consumer, I want to assign a task to a user.
3. As a API consumer, I want to complete a task.
4. As a API consumer, I want to fetch all tasks assigned to me.

API
===

1. Get all tasks `GET /v1/tasks`
2. Create task `POST /v1/tasks` (with prod profile, requires JWT token for `Tenant id` (`tasks-client`))
3. Assign a task to a user `POST /v1/tasks/{taskId}/assign/{userId}`
4. Complete a task `POST /v1/tasks/complete/{taskId}`
5. Fetch all tasks assigned to me `GET /v1/tasks/assigned/{userId}`
6. Fetch all tasks completed by user `GET /v1/tasks/assigned/{userId}/complete`
7. Update Task name, description. `PUT /v1/tasks/{taskId}`
8. Delete Task `DELETE /v1/tasks/{taskId}`

Tech stack
==========
- Spring Boot
- Spring MVC
- Spring Data JPA
- Lombok

Cloud
=====
- Spring Cloud
- Spring Cloud Config 'config-service'
- Spring Cloud Eureka
- Spring Cloud OAuth2
- Spring Cloud Zuul
- Spring Cloud Hystrix

- Added integration test for repository, service, boundary: `mvn integration-test`
- Exception handling with RestExceptionHandler
- Flayway for schema migration
- Swagger for API documentation `localhost:8080/swagger-ui.html`
- Added versioning `localhost:8080/v1/tasks`
- Added pagination and sorting for get all tasks
- Added Default (development) profile, Staging and Production (prod) with MySQL support and DB migration
- Actuator for service monitoring: `http://localhost:8080/health`, `http://localhost:8080/metrics`
- Added Docker support with Spotify maven plugin

    mvn clean package docker:build

What can be added:
+ HikariCP for Tomcat default Connection pool replacement
+ Querydsl for advanced query filtering


Classical microservices deployment with HAProxy
===============================================
Build HAProxy `cd tasks-service/docker/haproxy`
	
	docker build -t ha-proxy .

Create Docker network

	docker network create lb

Run tasks-service1

	docker run --net lb --rm --name tasks-service1 tasks-service
	

Build tasks-service 'cd ../..'

    mvn clean package docker:build
    
Run tasks-service1

	docker run --net lb --name tasks-service1 tasks-service
    
Run tasks-service2

	docker run --net lb --name tasks-service2 tasks-service


Run HAProxy
	
	docker run --net lb --name ha-proxy -p 8080:8080 -p 8989:8989 ha-proxy

Get stats for HAProxy

	http://localhost:8989/stats

Get all tasks

	http://localhost:8080/v1/tasks

Kill some service

	docker kill tasks-service2

Check HAProxy panel for one service down

	http://localhost:8989/stats
	

Cloud enabled deployment
========================
Build services

	mvn clean package docker:build

Run compose

	`cd tasks-service/docker/cloud-enabled`

    `docker-compose up`