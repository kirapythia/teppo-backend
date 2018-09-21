#!/bin/bash
# This file is used by Dockerfile

# Exit immediately if a command exits with a non-zero status
set -e

# Start supervisor (and thus gradle's continuous build)
sudo supervisord -c /etc/supervisord.conf

sleep 5

# if the first argument to `docker run` is bootRun, the user is passing gradle arguments
if [[ $# -lt 1 ]] || [[ "bootRun"  == *"$1"* ]]; then
    java \
        -Djava.security.egd=file:/dev/./urandom \
        -cp "/opt/flyway/lib/*" \
        org.flywaydb.commandline.Main \
        -locations="filesystem:/usr/src/app/src/main/resources/db/migrations" \
        -user="${FLYWAY_USERNAME:-postgres}" \
        -password="${FLYWAY_PASSWORD:-secret}" \
        -url="${FLYWAY_URL:-jdbc:postgresql://localhost:5433/teppo}" \
        -baselineOnMigrate=true \
        -placeholders.migration_user="${FLYWAY_PLACEHOLDERS_MIGRATION_USER:-postgres}" \
        -placeholders.application_user="${FLYWAY_PLACEHOLDERS_APPLICATION_USER:-tepposervice}" \
        migrate

    # Run as exec so the application can receive any Unix signals sent to the container, e.g.,
    # Ctrl + C.
    exec ./gradlew --no-daemon "$@"
fi

# As argument is not --env, assume user want to run his own process, for sample a `bash`
# shell to explore this image
exec "$@"
