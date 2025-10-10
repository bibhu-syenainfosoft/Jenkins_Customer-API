# FROM openjdk:17

# MAINTAINER Ecomm@Bibhu

# COPY target/Jenkins_Customer-API.jar  /usr/app/

# WORKDIR /usr/app/

# EXPOSE 8088

# ENTRYPOINT ["java", "-jar", "Jenkins_Customer-API.jar"]


# # Stage 1: Build
# FROM maven:3.9.9-eclipse-temurin-17 AS builder
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Stage 2: Runtime
# FROM eclipse-temurin:17-jre-alpine
# WORKDIR /usr/app
# COPY --from=builder /app/target/Jenkins_Customer-API.jar app.jar
# EXPOSE 8088
# ENTRYPOINT ["java", "-jar", "app.jar"]



FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="Ecomm@Bibhu"

WORKDIR /usr/app

COPY target/Jenkins_Customer-API.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]


