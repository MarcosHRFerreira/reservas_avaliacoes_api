 # Primeira etapa: Construir a aplicação
FROM eclipse-temurin:17-jammy AS build
WORKDIR /workspace
# Copie o pom.xml e baixe as dependências, isso melhora o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Copie o código fonte e construa o JAR
COPY src src

RUN mvn clean package -DskipTests

# Segunda etapa: Rodar a aplicação
FROM eclipse-temurin:17-jammy

EXPOSE 8080

COPY --from=build /workspace/target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]


