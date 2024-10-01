
FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
MAINTAINER Marcos Ferreira
WORKDIR /app
COPY target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]