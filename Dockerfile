FROM ubuntu:latest
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN apt-get update && apt-get install -y maven
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/reservas_avaliacoes_api.jar"]