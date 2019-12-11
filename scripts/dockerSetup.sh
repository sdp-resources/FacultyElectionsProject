#!/usr/bin/env bash
. vars.sh

# Start network, if not already started
docker network inspect ${NETWORK_NAME} >/dev/null 2>&1 || \
    docker network create --driver bridge ${NETWORK_NAME}
# Start DB and connect to network, if not already started
docker container inspect ${CONTAINER_DB} >/dev/null 2>&1 || \
    docker run -d --name ${CONTAINER_DB} \
        --network ${NETWORK_NAME} \
        -e MYSQL_DATABASE=${MYSQL_DATABASE} \
        -e MYSQL_USER=${MYSQL_USER} \
        -e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
        -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
        mariadb:10.4
# Start gradle daemon for compiling if not running
docker container inspect ${GRADLE_DAEMON} >/dev/null 2>&1 || \
    docker run -tdi --name ${GRADLE_DAEMON} \
        --network ${NETWORK_NAME} \
        --workdir /home/gradle/src \
        --mount type=bind,source="$(pwd)"/..,target=/home/gradle/src \
        gradle:6.0.1-jdk11 bash
# Compile
docker exec ${GRADLE_DAEMON} gradle --build-cache check installDist

# Build and run webserver container
docker build --network ${NETWORK_NAME} -t fecelections:latest \
    --file ../Dockerfile ../build/install/
docker stop ${CONTAINER_SERVER} >/dev/null 2>&1
docker run -d --rm --network ${NETWORK_NAME} \
    --name ${CONTAINER_SERVER} \
    -p 4567:4567 \
    fecelections:latest