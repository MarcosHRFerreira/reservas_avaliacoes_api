FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests

CMD ["java", "-jar", "target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

ENTRYPOINT ["java", "-jar", "reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar"]
