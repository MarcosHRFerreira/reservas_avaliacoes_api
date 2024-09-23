 # Primeira etapa: Construir a aplicação
FROM openjdk:17-jdk-alpine AS build

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

EXPOSE 8080

COPY --from=build /workspace/target/reservas_avaliacoes_api.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

