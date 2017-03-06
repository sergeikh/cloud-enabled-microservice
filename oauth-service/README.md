OAuth2 service
==============

In memory, password grand type
JSON Web Tokens to provide User's tenant ID
Used for authentication rest-client and protecting tasks service
(POST new Task method in tasks-service)

Get token 

    curl -XPOST "saas_app:@localhost:9999/uaa/oauth/token" -d "grant_type=password&username=sergei&password=secret"

Get token through routing service
    
    curl -XPOST "saas_app:@localhost:5555/api/auth/uaa/oauth/token" -d "grant_type=password&username=sergei&password=secret"

You can build an image with the above configurations by running this command.

    mvn clean package docker:build


