 # Primeira etapa: Construir a aplicação
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /workspace
# Copie o pom.xml e baixe as dependências, isso melhora o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Copie o código fonte e construa o JAR
COPY src src

RUN mvn clean package -DskipTests

# Segunda etapa: Rodar a aplicação
FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="marcos@marcos.net"
LABEL version="1.0"
LABEL description="FIAP - Tech Chalenger"
LABEL name="Reservas_Avaliacoes"
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/target/reservas_avaliacoes_api.jar"]
