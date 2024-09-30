FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
WORKDIR /app

COPY --from=build /target/reservas_avaliacoes_api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]




