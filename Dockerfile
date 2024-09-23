FROM maven:3.8.6-jdk-17-alpine
WORKDIR /app
COPY src src
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/reservas_avaliacoes_api.jar"]