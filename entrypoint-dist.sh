#!/bin/bash
# This file is used by Dockerfile

# Exit immediately if a command exits with a non-zero status
set -e


# if the first argument to `docker run` starts with `--`, now --name to give a name to the container initially
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then
    java \
        -Djava.security.egd=file:/dev/./urandom \
        -cp "/opt/flyway/lib/*" \
        org.flywaydb.commandline.Main \
        -locations="filesystem:/usr/src/app/src/main/resources/db/migrations" \
        -user="${FLYWAY_USERNAME}" \
        -password="${FLYWAY_PASSWORD}" \
        -url="${FLYWAY_URL}" \
        -baselineOnMigrate=true \
        -placeholders.application_user="${FLYWAY_PLACEHOLDERS_APPLICATION_USER}" \
        migrate

    unset FLYWAY_USERNAME
    unset FLYWAY_PASSWORD
    unset FLYWAY_URL
    unset FLYWAY_PLACEHOLDERS_APPLICATION_USER

    # Run as exec so the application can receive any Unix signals sent to the container, e.g., Ctrl + C.
    exec java -jar teppo-service.jar "$@"
fi

# As there is no argument beginning by  --, assume user want to run his own process, for example a `bash`
# shell to explore this image
exec "$@"
