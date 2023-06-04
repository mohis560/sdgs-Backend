FROM openjdk:17-jdk-slim-buster

LABEL mentainer="ms00866501@techmahindra.com"
USER root

WORKDIR /app

COPY target/synthetic-data-generator-service-0.0.1-SNAPSHOT.jar ./springboot-restful-webservices.jar
COPY target/application.properties ./application.properties

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=/app/application.properties","springboot-restful-webservices.jar"]
#ENTRYPOINT ["java", "-jar", "-Dspring.config.location=application.properties","springboot-restful-webservices.jar"]
