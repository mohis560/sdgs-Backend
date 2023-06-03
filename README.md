**DOCKER CASE1**

Deploy-spring-boot-mysql-application-to-docker

Reference:  https://www.javaguides.net/2022/12/deploy-spring-boot-mysql-application-to-docker.html  
Source Code:
```
cd Projects/synthetic-data-generator-service-Backend/
git pull
```
#Prerequistes Pull Base Image
```
docker pull mysql:8.0 openjdk:17-jdk-slim-buster
```
#CreateNetwork
```
docker network create springboot-mysql-net
docker network ls
```
#CreateMYSQLContainer in Detached Mode
```
docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=synthetic_data_service -d mysql:8.0
```
#LoginIntoMYSQL
```
docker exec -it mysqldb bash
mysql -u root -p
>show databases;
```
#CreateSpringBootImage using docker file
```
docker build -t synthetic-data-generator-service .
```
#CreateSpringBootContainer in Detached Mode
```
docker run --name sdgs1  --network springboot-mysql-net -p 8082:8080 synthetic-data-generator-service -d synthetic-data-generator-service
```



**DOCKER CASE2**

Deploy-spring-boot-mysql-application-to-docker Compose

Reference:  https://www.javaguides.net/2022/12/deploy-spring-boot-mysql-application-to-docker.html  
Source Code:

```
cd Projects/synthetic-data-generator-service-Backend/
git pull
```

Use Docker-compose.yml

```
version: "3.3"

services:
  mysqldb:
    container_name: mysqldb
    image: "mysql:8.0"
    ports:
     - 3306:3306
    environment:
      MYSQL_ROOT_USER: root
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: synthetic_data_service
    restart: on-failure
    networks:
      springboot-mysql-net:

  synthetic-data-generator-service:
    container_name: synthetic-data-generator-service
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
      - "8081:8080"
    depends_on:
      - mysqldb
    networks:
      springboot-mysql-net:
    restart: on-failure

networks:
  springboot-mysql-net:
```

#To Start container
```
Docker-compose up -d
```
#To Stop container
```
docker-compose down
```
