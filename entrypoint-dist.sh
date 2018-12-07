#!/bin/bash
# This file is used by Dockerfile

# Exit immediately if a command exits with a non-zero status
set -e


# if the first argument to `docker run` starts with `--`, now --name to give a name to the container initially
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then
    #Flyway commands here
    #RDS
    # -url="${FLYWAY_URL:-jdbc:postgresql://teppo-dev.cxlrhoid1g1j.eu-west-1.rds.amazonaws.com}" \

    # Run as exec so the application can receive any Unix signals sent to the container, e.g., Ctrl + C.
    # exec java -jar teppo-service.jar "$@"
    # Remove next line, use previous
    #exec ./gradlew --no-daemon "$@"
    exec java -jar teppo-service.jar "$@"
fi

# As there is no argument beginning by  --, assume user want to run his own process, for sexample a `bash`
# shell to explore this image
exec "$@"
