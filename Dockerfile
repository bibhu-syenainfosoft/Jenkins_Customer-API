FROM openjdk:17

MAINTAINER Ecomm@Bibhu

COPY target/Jenkins_Customer-API.jar  /usr/app/

WORKDIR /usr/app/

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Jenkins_Customer-API.jar"]
