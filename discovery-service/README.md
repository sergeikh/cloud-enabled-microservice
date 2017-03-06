Discovery Spring Eureka service
===============================

Provides service discovery and registration 

Client setup: `application-prod.properties`:

    eureka.instance.prefer-ip-address=true
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true
    # get from config-service
    #eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
Console http://dockerHost:8761
List of registred tasks services `http://dockerHost:8761/eureka/apps/tasks` 

Run

    mvn spring-boot:run

You can build an image with the above configurations by running this command.

    mvn clean package docker:build


