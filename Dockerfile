FROM openjdk:17-jdk-alpine
COPY target/*.jar /app.jar
ARG MAVEN_SKIP_TEST=false
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]