FROM khipu/openjdk17-alpine

RUN mkdir /app

COPY target/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar /app/reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

WORKDIR /app

CMD ls -l && java -jar reservas_avaliacoes_api-0.0.1-SNAPSHOT.jar
