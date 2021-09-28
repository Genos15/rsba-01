FROM maven:3.6.0-jdk-11-slim AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/home/app/target/*.jar
COPY --from=builder ${JAR_FILE} /usr/local/lib/app.jar
EXPOSE 9005/tcp
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]