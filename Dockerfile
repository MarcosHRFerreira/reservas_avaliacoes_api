 # Primeira etapa: Construir a aplicação
FROM openjdk:17-jdk-alpine

COPY target/reservas_avaliacoes_api.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]


