FROM eclipse-temurin:21-jdk-jammy
WORKDIR /opt/backend

COPY ./build/libs/consumer.jar consumer.jar
RUN true

ENTRYPOINT ["java", "-jar", "consumer.jar"]