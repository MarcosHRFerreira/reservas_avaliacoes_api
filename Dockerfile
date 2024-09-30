 # Primeira etapa: Construir a aplicação
FROM eclipse-temurin:17-jammy AS build
COPY . .

RUN ./mvnw clean install -DskipTests

RUN mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar"]

