#!/usr/bin/env bash
#
# Build docker image. You might need to login to ecr:
# eval $(aws --profile voltti-sst ecr get-login --no-include-email)
set -euo pipefail

DOCKER_IMAGE=${DOCKER_IMAGE:-teppo/teppo-service}
DOCKER_TAG=${DOCKER_TAG:-local}
GIT_SHA=$(git rev-parse HEAD)

rm -rf target
./gradlew -q assemble
unzip -oq build/libs/*.jar -d target

docker build -q -t "${DOCKER_IMAGE}:${DOCKER_TAG}" .
docker tag "${DOCKER_IMAGE}:${DOCKER_TAG}" "${DOCKER_IMAGE}:${GIT_SHA}"
