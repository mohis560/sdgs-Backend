**DOCKER CASE2**

Deploy-spring-boot-mysql-application-to-docker Compose

Reference:  https://www.javaguides.net/2022/12/deploy-spring-boot-mysql-application-to-docker.html  
Source Code:

cd Projects/synthetic-data-generator-service-Backend/
git pull

Docker-compose.yml

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

Docker-compose up# synthetic-data-generator-service-Backend
