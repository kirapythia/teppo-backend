# Never use "latest" as base container but specific tagged version instead
FROM anapsix/alpine-java:8u131b11_jdk


# Copy application JAR(s) to /app
COPY build/libs/pythia-backend.jar /app/pythia-backend.jar
COPY pythia-backend.sh /
# COPY pythia-backend.sh version.json /
RUN apk add --update bash &&\
    chmod +x /pythia-backend.sh

ENTRYPOINT ["/pythia-backend.sh"]
