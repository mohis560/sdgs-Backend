FROM eclipse-temurin:17

LABEL mentainer="ms00866501@techmahindra.com"

WORKDIR /app

COPY target/synthetic-data-generator-service-0.0.1-SNAPSHOT.jar /app/springboot-restful-webservices.jar
COPY target/application.properties /app/application.properties

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=application.properties","springboot-restful-webservices.jar"]