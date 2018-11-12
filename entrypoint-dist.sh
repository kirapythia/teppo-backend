#!/bin/bash
# This file is used by Dockerfile

# Exit immediately if a command exits with a non-zero status
set -e


# if the first argument to `docker run` starts with `--`, the user is passing spring launcher arguments
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then
    #Flyway commands here

    # Run as exec so the application can receive any Unix signals sent to the container, e.g., Ctrl + C.
    exec java -jar teppo-service.jar "$@"
fi

# As argument is not --env, assume user want to run his own process, for sample a `bash`
# shell to explore this image
exec "$@"
