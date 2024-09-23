# Etapa 1: Construir a aplicação
FROM openjdk:17-jdk-alpine AS build
WORKDIR /app
COPY src src
RUN mvn package

# Etapa 2: Rodar a aplicação
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /target/reservas_avaliacoes_api.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]