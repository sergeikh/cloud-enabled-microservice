Tasks client
==============

With Hystrix for client resiliency 
Get JWT token from OAuth2 service (with tenant ID)
Create new Task with tasks-service
(get token from oauth-service, create task through routing service)

    http://dockerHost:8082/client/tasks/create/    
    
API call

    http://dockerHost:5555/api/tasks-client/client/tasks/create

You can build an image with the above configurations by running this command.

    mvn clean package docker:build


