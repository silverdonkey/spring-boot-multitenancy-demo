FROM openjdk:12-jdk-alpine

RUN apk add --no-cache bash

# Will be substituted by CI during merge-master
ENV ARTIFACTVERSION 0.1.0-SNAPSHOT
# Possible values for SPRING_PROFILE local | socat
ENV SPRING_PROFILE socat

# Maven build
COPY target/*.jar /srv/service.jar

COPY run.sh /srv/run.sh
RUN chmod +x /srv/run.sh

WORKDIR /srv

EXPOSE 8080

ENTRYPOINT ["/srv/run.sh", "${SPRING_PROFILE}"]