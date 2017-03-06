Configuration service
=====================

Provides database connection information for tasks-service prod profile
Configuration files: `config/tasks-prod.properties`
Client setup: `application-prod.properties`:

    spring.cloud.config.uri=config-service:8888
Expose http://dockerHost:8888/tasks/prod

It's very unclear but file `bootstrap.yml`is loaded before configuration in *.properties on `Client`

`config-service` can be set through `env variable`

for more information: http://cloud.spring.io/spring-cloud-static/Brixton.SR5/#_spring_cloud_config_client

You can build an image with the above configurations by running this command.

    mvn clean package docker:build


