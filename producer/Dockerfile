FROM eclipse-temurin:21-jdk-jammy
WORKDIR /opt/backend

COPY ./build/libs/producer.jar producer.jar
RUN true

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "producer.jar"]