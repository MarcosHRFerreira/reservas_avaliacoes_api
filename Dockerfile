 # Primeira etapa: Construir a aplicação
FROM openjdk:17-jdk-alpine AS build

WORKDIR /workspace

# Copie o código fonte e construa o JAR
COPY src src
ARG MAVEN_SKIP_TEST=false

# Segunda etapa: Rodar a aplicação
FROM openjdk:17-jdk-alpine

EXPOSE 8080


COPY --from=build /target/reservas_avaliacoes_api.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

