
FROM maven:3.8.7-eclipse-temurin-19-alpine

WORKDIR /app

COPY . .

RUN mvn package

# Expose default Spring Boot port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/myapp-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]




