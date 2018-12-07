# Teppo service
Microservices-based REST interfaced service for DB storage access (Postgresql and S3) 
Unique Access to the mentioned data storages

## Importing gradle project to IntelliJ IDEA
<!-- TODO: Pythia is obsolete, refactor using Teppo instead -->
- JDK 1.8.0 update 131 is supported
- Import `teppo-service` (having `build.gradle` script) as Gradle project
- Make sure that `Use auto-import` is checked
- Build project after dependencies are loaded
- Run `PythiaBackendApplication`
- If needed, shorten command line as instructed in: https://blog.jetbrains.com/idea/2017/10/intellij-idea-2017-3-eap-configurable-command-line-shortener-and-more/ 

## Environments
TEPPO_ENV environment value is used for selecting which environment is used. Possible values:
- local (only this is currently available for db selection, other values point to default)
- dev
- test
- staging
- prod

## Building the project

```
./gradlew clean build 
```

## Starting the application

To start the application, run:

```
./gradlew bootRun
```

If you want to automatically restart Spring Boot whenever a file on the classpath changes, run the following command
in another terminal window:

```
./gradlew build --continuous
```

## Docker usage

There are different Dockerfiles for different use cases:

[Dockerfile](Dockerfile) is only meant to be used for local development. The
[docker-compose.yml](https://github.com/espoon-voltti/teppo-compose/blob/master/docker-compose.yml) file in the
[teppo-compose](https://github.com/espoon-voltti/teppo-compose/) repository uses this Dockerfile.

[Dockerfile.dist](Dockerfile.dist) is only meant to be used for distributing the app as a Docker image and running it,
e.g., in production.

We utilize the Docker builder pattern instead of using
[multi-stage Dockerfiles](https://docs.docker.com/v17.09/engine/userguide/eng-image/multistage-build/). Thus, we use
a separate Dockerfile for building the app and distributing the app. Multi-stage Dockerfiles have the benefit of
reducing the number of needed Dockerfiles. However, multi-stage Dockerfiles work best when the app is built and the
distribution image is created in a single step. On the other hand, the builder pattern works better when the build and
distribution images are created in separate steps, e.g., in build pipelines. In build pipelines, we have distinct
stages for building, testing, linting, and creating the distribution image. Of course, we could still utilize a
multi-stage Dockerfile for creating the distribution image but that would mean that we would unnecessarily have to
build the app twice. It is also possible to target separate stages in a multi-stage Dockerfile but that is not as
flexible as using the builder pattern.

### Run with Docker Compose

To run with Docker Compose, check the instructions in the
[NuorA Compose repository](https://github.com/espoon-voltti/teppo-compose/).

The development Dockerfile uses Spring Boot's developer tools and Gradle's continuous build feature to automatically
restart Spring Boot whenever files on the classpath change. Thus, you don't need to restart the Docker container
whenever you change a file.

Before you can run the example commands beneath, make sure you have built the necessary base images by building the
`docker-compose.yml` file in the NuorA Compose repository.

### Manually build and run the development container

To manually build and run [Dockerfile](Dockerfile), run:

```
# Get the ECR specific docker login command so that you can pull Voltti images from ECR.
$(aws --profile voltti-sst ecr get-login --no-include-email)

# Build the development image
docker build \
  -t teppo-service \
  .

# Run the app in the development image
docker run \
  --rm \
  -p 8081:8080 \
  -v `pwd`:/usr/src/app \
  --name teppo-service \
  teppo-service
```
