FROM openjdk:8-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9000/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
#
# Build stage
#
#FROM maven:3.8.1-jdk-8-slim AS build
##RUN mvn -f /home/app/pom.xml clean package
#RUN mvn compile
#RUN mvn clean install
#
#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=./target/*.jar
#COPY ${JAR_FILE} app.jar
#
##COPY --from=build ${JAVA_FILE} /usr/local/lib/app.jar
#
#EXPOSE 9001/tcp
#ENTRYPOINT ["java","-jar","/app.jar"]