FROM java:8-jdk-alpine

MAINTAINER sarath@gmail.com

COPY ./target/spring-boot-camel-0.0.1-SNAPSHOT.jar /Users/sarath/

WORKDIR /Users/sarath

RUN sh -c 'touch spring-boot-camel-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","spring-boot-camel-0.0.1-SNAPSHOT.jar"]

