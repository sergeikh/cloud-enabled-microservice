DB init
========================================

### Connection information:
**dbname**: taskservice
**user**:   user

### 1. Build image:
docker build -t taskservicedb .

### 2. Run DB container:
docker run -d --name taskservicedb -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=taskservice -e MYSQL_USER=user -e MYSQL_PASSWORD=123 taskservicedb

we do not expose any ports to our dockerHost: -p 3306:3306

### 2.1. Run TaskService app *(in production mode)* with *linked taskservicedb:*


