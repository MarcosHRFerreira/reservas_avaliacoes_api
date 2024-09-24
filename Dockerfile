 # Primeira etapa: Construir a aplicação
FROM openjdk:17-jdk-alpine
WORKDIR /workspace
# Copie o pom.xml e baixe as dependências, isso melhora o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Copie o código fonte e construa o JAR
COPY src src
ARG MAVEN_SKIP_TEST=false
RUN if [ "$MAVEN_SKIP_TEST" = "true" ] ; then mvn clean package -DskipTests ; else mvn clean package ; fi
# Segunda etapa: Rodar a aplicação
FROM openjdk:17-jdk-alpine
LABEL maintainer="marcos@marcos.net"
LABEL version="1.0"
LABEL description="FIAP - Tech Chalenger"
LABEL name="Reservas_Avaliacoes"
EXPOSE 8080
# Copie o JAR da primeira etapa
COPY /target/reservas_avaliacoes_api.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
