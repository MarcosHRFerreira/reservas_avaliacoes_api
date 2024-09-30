FROM eclipse-temurin:17-jammy
COPY . .
RUN ./mvnw clean install DskipTests

ENTRYPOINT ["java", "-jar", "target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar"]

