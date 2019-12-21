#!/usr/bin/env bash
### MOVING TO docker-compose. Do NOT use this file
. vars.sh
docker stop ${CONTAINER_DB} ${CONTAINER_SERVER} ${GRADLE_DAEMON}
docker rm ${CONTAINER_DB} ${CONTAINER_SERVER} ${GRADLE_DAEMON}
docker network rm ${NETWORK_NAME}