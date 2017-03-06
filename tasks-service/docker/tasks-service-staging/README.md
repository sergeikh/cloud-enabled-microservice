tasks-service image for Staging
===============================

###1. Build image:

docker build -t tasks-service .

###2. Run in development mode with internal in memory DB:
   
docker run -it --rm  --name task-service -p 8080:8080 tasks-service

or in detached mode (container will not be removed after **docker stop tasks-service**

docker run -d --name tasks-service -p 8080:8080 tasks-service

it will be accessible by **http://dockerHost:8080/**

###2. Run in staging mode with containers linking (DB):

**prerequisite:** staging DB should run on *dockerHost*.

docker run -d --name tasks-service -p 8080:8080 -e SPRING_PROFILES_ACTIVE=staging --link taskservicedb:db tasks-service

it will be accessible by **http://dockerHost:8080/**

###2.1 Run in interactive mode, and remove container after stop:

docker run --rm -it --name tasks-service -p 8080:8080 -e SPRING_PROFILES_ACTIVE=staging --link taskservicedb:db tasks-service

