#FROM openjdk:17

#MAINTAINER Ecomm@Bibhu

#COPY target/Jenkins_Customer-API.jar  /usr/app/

#WORKDIR /usr/app/

#EXPOSE 8088

#ENTRYPOINT ["java", "-jar", "Jenkins_Customer-API.jar"]





# -------- Stage 1: Build the JAR --------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies (cached if pom.xml unchanged)
RUN mvn dependency:go-offline -B

# Copy source code and build JAR
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Stage 2: Runtime image --------
FROM openjdk:17-jdk-slim

WORKDIR /usr/app

# Copy only the built JAR from Stage 1
COPY --from=builder /app/target/Jenkins_Customer-API.jar .

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Jenkins_Customer-API.jar"]
