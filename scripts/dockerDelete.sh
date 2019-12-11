#!/usr/bin/env bash
. vars.sh
docker stop ${CONTAINER_DB} ${CONTAINER_SERVER} ${GRADLE_DAEMON}
docker rm ${CONTAINER_DB} ${CONTAINER_SERVER} ${GRADLE_DAEMON}
docker network rm ${NETWORK_NAME}