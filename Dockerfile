FROM eclipse-temurin:17-jammy
WORKDIR /workspace
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn clean package -DskipTests

COPY --from=build /workspace/target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


