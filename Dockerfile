FROM openjdk:12-jdk-alpine

RUN apk add --no-cache bash

# Will be substituted by CI during merge-master
ENV ARTIFACTVERSION 0.1.0-SNAPSHOT
ENV MY_ENV local

# Maven build
COPY target/*.jar /srv/service.jar

COPY run.sh /srv/run.sh
RUN chmod +x /srv/run.sh

WORKDIR /srv

EXPOSE 8181

ENTRYPOINT ["/srv/run.sh", "${MY_ENV}"]