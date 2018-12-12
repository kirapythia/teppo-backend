#!/bin/bash

# Deploy by default to dev 
ENVIRONMENT="${1:-dev}"

# Get current version from cmd line or use git commit hash
GIT_HASH=$(git rev-parse --verify HEAD)
VERSION="${2:-$GIT_HASH}"

SERVICE_NAME="teppo-service"
IMAGE_NAME="teppo/service"
ECR_REPO="307238562370.dkr.ecr.eu-west-1.amazonaws.com"
IMAGE_URL="${ECR_REPO}/${IMAGE_NAME}:${VERSION}"
TERRAFORM_DIR="../teppo-infra/terraform"

# Login to ECR
aws --profile voltti-sst ecr get-login --no-include-email |sh

# Build java
./gradlew clean build -x test

# Build and tag docker image 
docker build -t $IMAGE_URL -f Dockerfile.dist .

# Push docker image
docker push $IMAGE_URL

# Init and run terraform
(cd $TERRAFORM_DIR && \
rm -rf .terraform
terraform init && \
(terraform workspace list | grep -w -q $ENVIRONMENT || terraform workspace new $ENVIRONMENT) && \
terraform workspace select $ENVIRONMENT && \
terraform apply --auto-approve -var ${SERVICE_NAME}_version=${VERSION})

echo "Deployed ${SERVICE_NAME} version ${VERSION}"
