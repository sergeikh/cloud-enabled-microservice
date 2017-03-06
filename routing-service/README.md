Routing service
===============

Maps tasks service route to a `/api` 
Dynamic routes mapping is enabled by Eureka 
Hystrix timeout `2500ms`
Ribbon timeout for tasks service `2500ms` 
Enabled propagation of the Authorization HTTP header to downstream services
Tasks route: `/api/tasks/`
OAuth2 route: `/api/auth/`

    
Expose http://dockerHost:5555/routes

Can be added: correlation id, routes filtering.
You can build an image with the above configurations by running this command.

    mvn clean package docker:build


