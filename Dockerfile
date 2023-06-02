FROM openjdk:17-jdk-slim-buster

LABEL mentainer="ms00866501@techmahindra.com"

WORKDIR /app

COPY target/* ./

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=/app/application.properties","synthetic-data-generator-service-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "-Dspring.config.location=application.properties","springboot-restful-webservices.jar"]