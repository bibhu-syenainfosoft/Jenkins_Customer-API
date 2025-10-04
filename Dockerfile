FROM openjdk:17

MAINTAINER Ecomm@Bibhu

COPY target/Customer-API.jar  /usr/app/

WORKDIR /usr/app/

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "Customer-API.jar"]