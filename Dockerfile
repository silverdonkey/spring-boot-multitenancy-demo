FROM openjdk:18-jdk-alpine

RUN apk add --no-cache bash

# Will be substituted by CI during merge-master
ENV ARTIFACTVERSION 0.1.0-SNAPSHOT
# Possible values for SPRING_PROFILE local | socat
ENV SPRING_PROFILE socat

RUN mkdir -p /srv
COPY target/*.jar /srv/service.jar
COPY run.sh /srv/run.sh
RUN chmod +x /srv/run.sh

# Add a NON-root user to run the app
RUN addgroup -S nonroot && adduser --disabled-password --system --uid 1000 --home /srv --gecos "" -S nonroot -G nonroot && chown -R nonroot:nonroot /srv

USER nonroot
WORKDIR /srv

EXPOSE 8080
#CMD ["/opt/openjdk-18/bin/java", "-jar", "-Dspring.profiles.active=default", "/srv/service.jar"]
ENTRYPOINT ["/srv/run.sh", "${SPRING_PROFILE}"]