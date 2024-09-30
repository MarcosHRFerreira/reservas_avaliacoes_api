FROM eclipse-temurin:17-jammy
COPY . .
RUN ./mvn clean install DskipTests

ENTRYPOINT ["java", "-jar", "target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar"]

