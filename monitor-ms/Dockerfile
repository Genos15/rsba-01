FROM openjdk:8-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9050/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
