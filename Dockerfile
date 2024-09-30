FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
WORKDIR /app
COPY target/reservas_avaliacoes_api.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]


EXPOSE 8080

