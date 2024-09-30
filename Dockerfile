FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests
ENTRYPOINT ["java", "-jar", "/reservas_avaliacoes_api.jar"]


